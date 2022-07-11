package ru.stoloto.esb.webkaf.service.producer;

import lombok.NonNull;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface TopicProducer {

    ListenableFuture<SendResult<String, String>> produce(String payload, String brokerUrl, @NonNull String topic);

}
