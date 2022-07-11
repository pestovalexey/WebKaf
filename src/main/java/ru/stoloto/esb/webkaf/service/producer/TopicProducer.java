package ru.stoloto.esb.webkaf.service.producer;

import lombok.NonNull;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Producer
 */
public interface TopicProducer {

    /**
     * @param payload   Payload to produce
     * @param brokerUrl Broker or Queue Manager URL
     * @param topic     Producer Topic name
     *
     * @return          Future object to acquire Offset result later
     */
    ListenableFuture<SendResult<String, Object>> produce(Object payload, String brokerUrl, @NonNull String topic);
}
