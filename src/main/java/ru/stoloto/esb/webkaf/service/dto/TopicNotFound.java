package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public class TopicNotFound implements BindingOperationResult {

    @Override
    public @NonNull WebKafState getCode() {
        return WebKafState.TOPIC_NOT_FOUND;
    }

    @Override
    public String getInfo() {
        return "Topic not found";
    }
}
