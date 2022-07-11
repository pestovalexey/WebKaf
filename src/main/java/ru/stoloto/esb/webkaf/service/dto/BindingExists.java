package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public class BindingExists implements BindingOperationResult {
    @Override
    public @NonNull WebKafState getCode() {
        return WebKafState.BINDING_EXIST;
    }

    @Override
    public String getInfo() {
        return "Binding already exists";
    }
}
