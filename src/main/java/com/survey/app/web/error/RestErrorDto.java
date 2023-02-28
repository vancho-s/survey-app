package com.survey.app.web.error;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@ApiModel("RestErrorDto")
public class RestErrorDto {
    private String code;
    private String message;
}
