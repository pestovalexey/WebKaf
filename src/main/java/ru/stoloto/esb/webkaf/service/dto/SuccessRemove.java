package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public class SuccessRemove implements BindingOperationResult {

    public SuccessRemove(String uri) {
        this.uri = uri;
    }

    @Override
    public @NonNull WebKafState getCode() {
        return WebKafState.BINDING_SUCCESS;
    }

    @Override
    public String getInfo() {
        return uri;
    }

    private final String uri;
}
