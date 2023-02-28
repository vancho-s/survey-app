package com.survey.app.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuestionResponse {
    private Long subjectId;
    private List<QuestionCreateDto> subjectQuestions;
}
