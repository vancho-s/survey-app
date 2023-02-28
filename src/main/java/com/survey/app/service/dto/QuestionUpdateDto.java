package com.survey.app.service.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuestionUpdateDto {
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long questionId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String questionLabel;

    @NotNull
    private Boolean enabled;
}
