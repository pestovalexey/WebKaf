package ru.stoloto.esb.webkaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import ru.stoloto.esb.webkaf.api.v1.BindingRequest;
import ru.stoloto.esb.webkaf.api.v1.BindingResponse;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
class ApplicationApiControllerTest extends WebKafBaseTest {

    public ApplicationApiControllerTest(@Autowired RestTemplate restTemplate,
                                        @Autowired ObjectMapper jsonMapper,
                                        @LocalServerPort int port) {
        this.restTemplate = restTemplate;
        this.jsonMapper = jsonMapper;
        this.port = port;
    }

    @BeforeEach
    void addConfig() throws Exception {
        URL url = new URL(http, host, port, "/webkaf/api/v1/addConfig");

        ResponseEntity<BindingResponse> response = restTemplate.postForEntity(url.toURI(),
                DummyObjectFactory.createAddConfigRequestPost().getBody(), BindingResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getConfig() throws Exception {
        URL url = new URL(http, host, port, "/webkaf/api/v1/getConfig");

        HttpEntity<BindingRequest> requestPost = DummyObjectFactory.createGetConfigRequestPost();
        ResponseEntity<WebKafBinding> response = restTemplate.postForEntity(url.toURI(), requestPost,
                WebKafBinding.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @AfterEach
    void delConfig() throws Exception {
        URL url = new URL(http, host, port, "/webkaf/api/v1/delConfig");

        HttpEntity<String> requestPost = DummyObjectFactory.createDelConfigRequestPost();
        ResponseEntity<BindingResponse> response = restTemplate.postForEntity(url.toURI(), requestPost,
                BindingResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    final RestTemplate restTemplate;
    final ObjectMapper jsonMapper;

    final String host = "localhost";
    final String http = "http";
    final int port;
}