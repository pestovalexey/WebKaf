package ru.stoloto.esb.webkaf.service;


import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.service.dto.BindingOperationResult;

public interface BindingManager {

    @Nullable
    BindingOperationResult createBinding(@NonNull WebKafBinding binding);

    @Nullable
    BindingOperationResult removeBinding(@NonNull String uri);

    @Nullable
    WebKafBinding getBinding(@NonNull String uri);
}
