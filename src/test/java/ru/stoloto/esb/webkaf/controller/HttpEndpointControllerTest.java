package ru.stoloto.esb.webkaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
class HttpEndpointControllerTest {
    public HttpEndpointControllerTest(@Autowired RestTemplate restTemplate,
                                      @Autowired ObjectMapper jsonMapper,
                                      @LocalServerPort int port) {
        this.restTemplate = restTemplate;
        this.jsonMapper = jsonMapper;
        this.port = port;
    }

    @Test
    @Disabled
    void testConsumeProduce() throws URISyntaxException, MalformedURLException {
        URL url = new URL(http, host, port, DummyObjectFactory.createTestUri());

        Long offset = restTemplate.postForObject(url.toURI(), "Hello, WebKaf", Long.class);
        assertThat(offset).isNotNull();
    }

    @BeforeEach
    void addConfig() throws Exception {
        new ApplicationApiControllerTest(restTemplate, jsonMapper, port).addConfig();
    }

    final RestTemplate restTemplate;
    final ObjectMapper jsonMapper;

    final String host = "localhost";
    final String http = "http";
    final int port;
}