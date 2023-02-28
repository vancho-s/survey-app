package com.survey.app.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SurveyResponsesDto {
    private long userId;
    private List<SurveyResponseDto> responses;
}
