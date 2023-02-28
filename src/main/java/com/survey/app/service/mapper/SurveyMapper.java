package com.survey.app.service.mapper;

import com.survey.app.domain.Subject;
import com.survey.app.domain.UserResponse;
import com.survey.app.service.dto.SurveyResponseDto;
import com.survey.app.service.dto.SurveyResponsesDto;
import com.survey.app.service.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Component
public class SurveyMapper {

    public SurveyResponsesDto toSurveyResponse(final long userId, final Map<Long, SurveyResponseDto> responsesAggr) {
        SurveyResponsesDto responsesDto = new SurveyResponsesDto();
        responsesDto.setUserId(userId);
        List<SurveyResponseDto> responsesList = responsesAggr.values().stream()
            .sorted(comparing(SurveyResponseDto::getSubjectId)).collect(Collectors.toList());
        responsesDto.setResponses(responsesList);
        return responsesDto;
    }

    public Map<Long, SurveyResponseDto> aggregateResponsesBySurveyId(List<UserResponse> responses) {
        return responses.stream().sorted(Comparator.comparing(response -> response.getQuestion().getId()))
                .collect(groupingBy(response -> response.getQuestion().getSubject().getId(),
                    collectingAndThen(Collectors.toList(), responsesAgr -> {
                        Subject subject = responsesAgr.stream().map(response -> response.getQuestion().getSubject())
                            .findFirst().get();

                        List<UserResponseDto> answersToSurvey = responsesAgr.stream()
                            .map(response -> new UserResponseDto(response.getId(), response.getQuestion().getId(),
                                response.getQuestion().getLabel(), response.getContent())).collect(Collectors.toList());
                        return new SurveyResponseDto(subject.getId(), subject.getSubjectLabel(), answersToSurvey);
                    })));
    }
}
