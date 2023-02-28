package com.survey.app.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SubjectCreateResponseDto {
    private Long subjectId;
    private String subjectLabel;
    private List<QuestionCreateDto> questions;
}
