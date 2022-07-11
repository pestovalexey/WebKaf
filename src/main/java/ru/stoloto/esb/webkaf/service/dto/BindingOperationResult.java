package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

public interface BindingOperationResult {

    @NonNull
    WebKafState getCode();
    String getInfo();

}
