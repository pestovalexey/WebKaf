package ru.stoloto.esb.webkaf.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "WebKaf Binding configuration. It binds HTTP Server's URI to Kafka topic, so that POST's payload on that URI is going to be produced to the topic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebKafBinding {

    @ApiModelProperty(required = true, value = "Идентификатор конфигурации")
    @JsonProperty("id")
    private long id;

    @ApiModelProperty(required = true, value = "Код группы интеграции")
    @JsonProperty("cii_code")
    private String ciiCode;

    @ApiModelProperty(required = true, value = "Имя группы интеграции")
    @JsonProperty("cii_group")
    private String ciiGroup;

    @ApiModelProperty(required = true, value = "Http URI нового Endpoint, который приложение будет слушать")
    @JsonProperty("endpoint_uri")
    private String endpointUri;

    @ApiModelProperty(required = true, value = "URL сервера Kafka для публикации данных из тела POST запроса, пришедшего на endpoint_uri")
    @JsonProperty("kafka_url")
    private String kafkaUrl;

    @ApiModelProperty(required = true, value = "Имя топика Kafka для публикации данных из тела POST запроса, пришедшего на endpoint_uri")
    @JsonProperty("kafka_topic")
    private String kafkaTopic;
}

