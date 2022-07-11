package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public class BindingNotFound implements BindingOperationResult {

    @Override
    public @NonNull WebKafState getCode() {
        return WebKafState.BINDING_NOT_FOUND;
    }

    @Override
    public String getInfo() {
        return "Binding not found";
    }
}
