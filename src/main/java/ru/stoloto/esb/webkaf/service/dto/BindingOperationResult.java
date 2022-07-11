package ru.stoloto.esb.webkaf.service.dto;

import lombok.NonNull;
import ru.stoloto.esb.webkaf.api.v1.WebKafState;

/**
 * Result of controller's operation execution
 */
public interface BindingOperationResult {

    /**
     *
     * @return Operation result code
     */
    @NonNull
    WebKafState getCode();

    /**
     *
     * @return Operation result information
     */
    String getInfo();
}
