package com.survey.app.web.error;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RestFieldErrorDto {
    private String field;
    private String code;
    private String message;
}
