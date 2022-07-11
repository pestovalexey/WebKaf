package ru.stoloto.esb.webkaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.service.producer.KafkaTopicProducer;
import ru.stoloto.esb.webkaf.service.producer.TopicProducer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebKafConsumerController {
    public WebKafConsumerController(@Autowired KafkaTopicProducer kafkaTopicProducer,
                                    @Autowired Map<String, WebKafBinding> uriToBinding) {
        this.uriToBinding = uriToBinding;
        this.kafkaTopicProducer = kafkaTopicProducer;
    }

    @PostMapping(path = "/**")
    public @ResponseBody Long consume(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebKafBinding binding = uriToBinding.get(request.getRequestURI());
        if (binding == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        var future = kafkaTopicProducer.produce(toPayload(request), binding.getKafkaUrl(), binding.getKafkaTopic());
        var offset = future.get()
                .getRecordMetadata()
                .offset();

        response.setStatus(HttpServletResponse.SC_OK);
        return offset;
    }

    private String toPayload(HttpServletRequest request) throws IOException {
        return request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private final TopicProducer kafkaTopicProducer;
    private final Map<String, WebKafBinding> uriToBinding;
}