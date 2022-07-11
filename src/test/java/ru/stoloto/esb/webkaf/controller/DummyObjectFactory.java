package ru.stoloto.esb.webkaf.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.api.v1.BindingRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DummyObjectFactory {

    static HttpEntity<WebKafBinding> createAddConfigRequestPost() {
        return new HttpEntity<>(DummyObjectFactory.createAddConfigRequestBody(),
                DummyObjectFactory.createHttpRequestHeader());
    }

    static HttpEntity<BindingRequest> createGetConfigRequestPost() {
        return new HttpEntity<>(new BindingRequest(createTestUri()), createHttpRequestHeader());
    }

    static HttpEntity<String> createDelConfigRequestPost() {
        return new HttpEntity<>(createTestUri(), createHttpRequestHeader());
    }

    static HttpHeaders createHttpRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        header.setAccept(List.of(MediaType.APPLICATION_JSON));
        return header;
    }

    static WebKafBinding createAddConfigRequestBody() {
        return new WebKafBinding(
                createNextId(),
                createNextCiiCode(),
                "alpsGroup",
                createTestUri(),
                createTestKafka(),
                createTestTopic());
    }

    static long createNextId() {
        return new Random(98L).nextLong();
    }

    static String createNextCiiCode() {
        return UUID.randomUUID().toString();
    }

    static String createTestUri() {
        return "/webkaf/alps/endpoint";
    }

    static String createTestTopic() {
        return "alps-topic";
    }

    static String createTestKafka() {
        return "localhost:9092";
    }
}
