package ru.stoloto.esb.webkaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.service.producer.KafkaTopicProducer;
import ru.stoloto.esb.webkaf.service.producer.TopicProducer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HttpEndpointController implements EndpointConsumer {
    public HttpEndpointController(@Autowired KafkaTopicProducer kafkaTopicProducer,
                                  @Autowired Map<String, WebKafBinding> uriToBinding) {
        this.uriToBinding = uriToBinding;
        this.kafkaTopicProducer = kafkaTopicProducer;
    }

    @Override
    @PostMapping(path = "/**")
    public @ResponseBody Long consume(ServletRequest request, ServletResponse response) throws Exception {
        WebKafBinding binding = uriToBinding.get(((HttpServletRequest) request).getRequestURI());
        if (binding == null) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        var future = kafkaTopicProducer.produce(toPayload(request), binding.getKafkaUrl(), binding.getKafkaTopic());
        var offset = future.get()
                .getRecordMetadata()
                .offset();

        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
        return offset;
    }

    private String toPayload(ServletRequest request) throws IOException {
        return request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private final TopicProducer kafkaTopicProducer;
    private final Map<String, WebKafBinding> uriToBinding;
}