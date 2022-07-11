package ru.stoloto.esb.webkaf.service;


import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.service.dto.BindingOperationResult;

/**
 * WebKaf application service management interface
 *
 */
public interface BindingManager {

    /**
     * Create new binding configuration
     *
     * @param binding configuration
     * @return binding creation result
     */
    @Nullable
    BindingOperationResult createBinding(@NonNull WebKafBinding binding);

    /**
     * Stop listening for uri. Remove binding configuration
     *
     * @param uri Binding endpoint URI to remove
     * @return binding remove result
     */
    @Nullable
    BindingOperationResult removeBinding(@NonNull String uri);

    /**
     *
     * @param uri Binding endpoint URI to get
     * @return WebKaf binding configuration object
     */
    @Nullable
    WebKafBinding getBinding(@NonNull String uri);
}
