package ru.stoloto.esb.webkaf.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Ответ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingResponse {

    @ApiModelProperty(required = true, value = "Код ответа")
    @JsonProperty("response_code")
    private Integer code;

    @ApiModelProperty(required = true, value = "Текст ответа")
    @JsonProperty("response_text")
    private String text;
}
