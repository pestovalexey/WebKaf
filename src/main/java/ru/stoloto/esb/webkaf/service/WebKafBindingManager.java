package ru.stoloto.esb.webkaf.service;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;
import ru.stoloto.esb.webkaf.service.dto.BindingExists;
import ru.stoloto.esb.webkaf.service.dto.BindingNotFound;
import ru.stoloto.esb.webkaf.service.dto.BindingOperationResult;
import ru.stoloto.esb.webkaf.service.dto.SuccessCreate;
import ru.stoloto.esb.webkaf.service.dto.SuccessRemove;
import ru.stoloto.esb.webkaf.service.dto.TopicNotFound;

import java.util.Map;

@Service
public class WebKafBindingManager implements BindingManager {

    public WebKafBindingManager(@Autowired Map<String, WebKafBinding> uriToBinding) {
        this.index = uriToBinding;
    }

    @Override
    public @Nullable BindingOperationResult createBinding(@NonNull WebKafBinding binding) {
        if (index.putIfAbsent(binding.getEndpointUri(), binding) != null) {
            return new BindingExists();
        }
        if (notFound(binding.getKafkaTopic())) {
            return new TopicNotFound();
        }

        //TODO return null; timeout при обращении к key-value store
        return new SuccessCreate();
    }

    @Override
    public @Nullable BindingOperationResult removeBinding(@NonNull String uri) {
        WebKafBinding binding = index.remove(uri);
        if (binding == null) {
            return new BindingNotFound();
        }

        //TODO return null; timeout при обращении к key-value store
        return new SuccessRemove(binding.getEndpointUri());
    }

    @Override
    public @Nullable WebKafBinding getBinding(@NonNull String uri) {
        return index.get(uri);
    }

    private static boolean notFound(String topic) {
        //TODO return new Random(topic.length()).nextBoolean();
        return Boolean.FALSE;
    }

    private final Map<String, WebKafBinding> index;
}
