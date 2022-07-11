package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public class SuccessCreate implements BindingOperationResult {

    @Override
    public @NonNull WebKafState getCode() {
        return WebKafState.BINDING_SUCCESS;
    }

    @Override
    public String getInfo() {
        return "Binding successfully created";
    }
}

