package ru.stoloto.esb.webkaf.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.stoloto.esb.webkaf.api.v1.WebKafBinding;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebMvc
public class ApplicationConfiguration {

    @Bean
    public HttpMessageConverter<?> httpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setObjectMapper(objectMapper);
        converter.setPrettyPrint(true);
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
        module.addSerializer(new LocalDateTimeSerializer(dateFormatter));
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module);
    }

    @Bean
    public RestTemplate restTemplate(HttpMessageConverter<?> httpMessageConverter) {
        return new RestTemplate(List.of(httpMessageConverter));
    }

    @Bean
    public Map<String, WebKafBinding> uriToBinding() {
        return new ConcurrentHashMap<>();
    }
}
