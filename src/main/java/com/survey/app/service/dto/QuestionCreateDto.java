package com.survey.app.service.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuestionCreateDto {
    private Long questionId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String questionLabel;

    @NotNull
    private Boolean enabled;
}
