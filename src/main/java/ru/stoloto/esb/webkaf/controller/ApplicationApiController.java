package ru.stoloto.esb.webkaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stoloto.esb.webkaf.api.v1.BindingRequest;
import ru.stoloto.esb.webkaf.api.v1.BindingResponse;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;
import ru.stoloto.esb.webkaf.service.BindingManager;
import ru.stoloto.esb.webkaf.service.dto.BindingOperationResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", consumes = "application/json", produces = "application/json")
public class ApplicationApiController {
    public ApplicationApiController(@Autowired ObjectMapper jsonMapper,
                                    @Autowired BindingManager bindingManager) {
        this.jsonMapper = jsonMapper;
        this.bindingManager = bindingManager;
    }

    @PostMapping(path = "/addConfig")
    public @ResponseBody ResponseEntity<BindingResponse> addConfig(@RequestBody WebKafBinding request) {
        BindingOperationResult result = bindingManager.createBinding(request);
        if (result == null) {
            return timeout();
        }

        switch (result.getCode()) {
            case BINDING_SUCCESS: {
                log.debug("Binding added {}", request.getEndpointUri());
                return success();
            }
            case BINDING_EXIST: {
                log.error("Binding exists {}", request.getEndpointUri());
                return badRequest();
            }
            case TOPIC_NOT_FOUND: {
                log.error("Topic not found {}", request.getKafkaTopic());
                return badRequest();
            }
            case BINDING_ERROR: {
                log.error("");
                return serverError();
            }
            default:
                log.error("Unsupported result code {}", result.getInfo());
                return serverError();
        }
    }

    @PostMapping(path = "/delConfig")
    public @ResponseBody ResponseEntity<BindingResponse> delConfig(@RequestBody BindingRequest request) {
        String requestUri = request.getUri();
        BindingOperationResult response = bindingManager.removeBinding(requestUri);
        if (response == null) {
            return timeout();
        }

        WebKafState responseCode = response.getCode();
        switch (responseCode) {
            case BINDING_SUCCESS: {
                log.debug("Binding removed {}", requestUri);
                return success();
            }
            case BINDING_NOT_FOUND: {
                log.warn("Binding not found {}", requestUri);
                return notFound();
            }
            case BINDING_ERROR: {
                log.error("Internal server error {}", requestUri);
                return serverError();
            }
            default: {
                log.error("Unsupported response code {}", responseCode);
                return serverError();
            }
        }
    }

    @PostMapping(path = "/getConfig")
    public @ResponseBody ResponseEntity<WebKafBinding> getConfig(@RequestBody BindingRequest request) {
        WebKafBinding result = bindingManager.getBinding(request.getUri());
        if (result == null) {
            return new ResponseEntity<>(createResponseHeader(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, createResponseHeader(), HttpStatus.OK);
    }

    private static ResponseEntity<BindingResponse> serverError() {
        return new ResponseEntity<>(new BindingResponse(WebKafState.BINDING_ERROR.toNumber(), ""),
                createResponseHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<BindingResponse> badRequest() {
        return new ResponseEntity<>(new BindingResponse(WebKafState.BINDING_EXIST.toNumber(), ""),
                createResponseHeader(), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<BindingResponse> notFound() {
        return new ResponseEntity<>(new BindingResponse(WebKafState.BINDING_NOT_FOUND.toNumber(), ""),
                createResponseHeader(), HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<BindingResponse> success() {
        return new ResponseEntity<>(new BindingResponse(0, ""), createResponseHeader(), HttpStatus.OK);
    }

    private static HttpHeaders createResponseHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(List.of(MediaType.APPLICATION_JSON));
        header.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        return header;
    }

    private final ObjectMapper jsonMapper;
    private final BindingManager bindingManager;

    private static ResponseEntity<BindingResponse> timeout() {
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }
}
