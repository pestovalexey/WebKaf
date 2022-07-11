package ru.stoloto.esb.webkaf.service.producer;

import lombok.NonNull;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface TopicProducer {

    /**
     * @param payload   Payload to produce
     * @param brokerUrl Broker or queue manager URL
     * @param topic     Producer topic name
     * @return          Future object to acquire result asynchronously
     */
    ListenableFuture<SendResult<String, Object>> produce(Object payload, String brokerUrl, @NonNull String topic);
}
