package com.survey.app.service.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuestionUpdateRequest {
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long subjectId;

    @NotNull
    private List<@Valid QuestionUpdateDto> subjectQuestions;
}
