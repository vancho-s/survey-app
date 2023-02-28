package com.survey.app.service.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SubjectCreateDto {
    private Long subjectId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String subjectLabel;
}
