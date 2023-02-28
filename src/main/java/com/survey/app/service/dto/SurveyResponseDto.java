package com.survey.app.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SurveyResponseDto {
    private Long subjectId;
    private String surveyTitle;
    private List<UserResponseDto> questions;
}
