package ru.stoloto.esb.webkaf.service.producer;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaTopicProducer implements TopicProducer {
    public KafkaTopicProducer(@Autowired KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ListenableFuture<SendResult<String, Object>> produce(Object payload, String brokerUrl, @NonNull String topic) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, payload);
        future.addCallback(
                new ListenableFutureCallback<>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        RecordMetadata metadata = result.getRecordMetadata();
                        log.info("Message produced: payload={}, offset={}", payload, metadata.offset());
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        log.error("Unable to produce message, cause '{}'", t.getMessage());
                    }
                });
        return future;
    }

    private final KafkaTemplate<String, Object> kafkaTemplate;
}
