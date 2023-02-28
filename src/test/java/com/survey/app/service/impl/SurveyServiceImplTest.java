package com.survey.app.service.impl;

import com.survey.app.domain.UserResponse;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.SurveyService;
import com.survey.app.service.dto.SurveyResponseDto;
import com.survey.app.service.dto.SurveyResponsesDto;
import com.survey.app.service.mapper.SurveyMapper;
import com.survey.app.service.validator.SubjectValidator;
import com.survey.app.service.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyServiceImplTest {
    private static final long SURVEY_ID = 10L;
    private static final long USER_ID = 1L;

    @Mock
    private UserValidator userValidatorMock;
    @Mock
    private SubjectValidator subjectValidatorMock;
    @Mock
    private UserResponseRepository userResponseRepositoryMock;
    @Mock
    private SurveyMapper surveyMapperMock;
    @Mock
    private UserResponse userResponseMock;
    @Mock
    private SurveyResponseDto surveyResponseDtoMock;
    @Mock
    private SurveyResponsesDto  surveyResponsesDtoMock;

    private SurveyService testInstance;

    @BeforeEach
    void init() {
        testInstance = new SurveyServiceImpl(userValidatorMock, subjectValidatorMock, userResponseRepositoryMock,
            surveyMapperMock);
    }

    @Test
    void shouldFindSurveysBySubjectIdsAndUserIds() {
        Set<Long> subjectIds = Set.of(SURVEY_ID);
        List<UserResponse> userResponses = List.of(userResponseMock);
        when(userResponseRepositoryMock.findResponsesByUserIdAndSubjectsIds(USER_ID, subjectIds)).thenReturn(userResponses);
        List<SurveyResponseDto> surveyResponseDtoList = List.of(surveyResponseDtoMock);
        when(surveyResponsesDtoMock.getResponses()).thenReturn(surveyResponseDtoList);
        Map<Long, SurveyResponseDto> responseAggregations = Map.of(SURVEY_ID, surveyResponseDtoMock);
        when(surveyMapperMock.aggregateResponsesBySurveyId(userResponses)).thenReturn(responseAggregations);
        when(surveyMapperMock.toSurveyResponse(USER_ID, responseAggregations)).thenReturn(surveyResponsesDtoMock);

        SurveyResponsesDto result = testInstance.findAllSurveysByUserIdAndSubjectIds(USER_ID, subjectIds);

        assertFalse(result.getResponses().isEmpty());
        assertEquals(surveyResponseDtoMock, result.getResponses().get(0));
        verify(userValidatorMock).verifyUserExist(USER_ID);
        verify(subjectValidatorMock).verifySubjectsExist(subjectIds);
        verify(subjectValidatorMock).verifyResponsesForSubjectsExist(Set.of(SURVEY_ID), subjectIds);
        verify(userResponseRepositoryMock).findResponsesByUserIdAndSubjectsIds(USER_ID, subjectIds);
        verify(surveyMapperMock).aggregateResponsesBySurveyId(userResponses);
        verify(surveyMapperMock).toSurveyResponse(USER_ID, responseAggregations);
    }

    @Test
    void shouldFindAllSurveysByByUserIds() {
        List<UserResponse> userResponses = List.of(userResponseMock);
        when(userResponseRepositoryMock.findUserResponsesByUserId(USER_ID)).thenReturn(userResponses);
        List<SurveyResponseDto> surveyResponseDtoList = List.of(surveyResponseDtoMock);
        when(surveyResponsesDtoMock.getResponses()).thenReturn(surveyResponseDtoList);

        Map<Long, SurveyResponseDto> responseAggregations = Map.of(SURVEY_ID, surveyResponseDtoMock);
        when(surveyMapperMock.aggregateResponsesBySurveyId(userResponses)).thenReturn(responseAggregations);
        when(surveyMapperMock.toSurveyResponse(USER_ID, responseAggregations)).thenReturn(surveyResponsesDtoMock);

        SurveyResponsesDto result = testInstance.findAllSurveysByUserId(USER_ID);

        assertFalse(result.getResponses().isEmpty());
        assertEquals(surveyResponseDtoMock, result.getResponses().get(0));
        verify(userValidatorMock).verifyUserExist(USER_ID);
        verify(userResponseRepositoryMock).findUserResponsesByUserId(USER_ID);
        verify(surveyMapperMock).aggregateResponsesBySurveyId(userResponses);
        verify(surveyMapperMock).toSurveyResponse(USER_ID, responseAggregations);
    }
}
