package com.survey.app.service.impl;

import com.survey.app.domain.UserResponse;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.SurveyService;
import com.survey.app.service.dto.SurveyResponseDto;
import com.survey.app.service.dto.SurveyResponsesDto;
import com.survey.app.service.mapper.SurveyMapper;
import com.survey.app.service.validator.SubjectValidator;
import com.survey.app.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyServiceImpl.class);
    private final UserValidator userValidator;
    private final SubjectValidator subjectValidator;
    private final UserResponseRepository userResponseRepository;
    private final SurveyMapper surveyMapper;

    public SurveyServiceImpl(final UserValidator userValidator, final SubjectValidator subjectValidator,
                             final UserResponseRepository userResponseRepository, final SurveyMapper surveyMapper) {
        this.userValidator = userValidator;
        this.subjectValidator = subjectValidator;
        this.userResponseRepository = userResponseRepository;
        this.surveyMapper = surveyMapper;
    }

    @Override
    public SurveyResponsesDto findAllSurveysByUserIdAndSubjectIds(final long userId, final Set<Long> subjectIds) {
        LOGGER.debug("Return surveys for user {} with survey ids {}", userId, subjectIds);
        userValidator.verifyUserExist(userId);
        subjectValidator.verifySubjectsExist(subjectIds);
        List<UserResponse> responses = userResponseRepository.findResponsesByUserIdAndSubjectsIds(userId, subjectIds);
        Map<Long, SurveyResponseDto> responseAggregations = surveyMapper.aggregateResponsesBySurveyId(responses);
        subjectValidator.verifyResponsesForSubjectsExist(responseAggregations.keySet(), subjectIds);
        return surveyMapper.toSurveyResponse(userId, responseAggregations);
    }

    @Override
    public SurveyResponsesDto findAllSurveysByUserId(final long userId) {
        LOGGER.debug("Return all surveys for user {}", userId);
        userValidator.verifyUserExist(userId);
        List<UserResponse> responses = userResponseRepository.findUserResponsesByUserId(userId);
        Map<Long, SurveyResponseDto> responseAggregations = surveyMapper.aggregateResponsesBySurveyId(responses);
        return surveyMapper.toSurveyResponse(userId, responseAggregations);
    }
}
