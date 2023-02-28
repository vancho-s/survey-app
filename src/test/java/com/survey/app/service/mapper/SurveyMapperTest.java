package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.Subject;
import com.survey.app.domain.UserResponse;
import com.survey.app.service.dto.SurveyResponseDto;
import com.survey.app.service.dto.SurveyResponsesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyMapperTest {
    private static final long USER_ID = 1L;
    private static final long SUBJECT_ID1 = 10L;
    private static final long SUBJECT_ID2 = 11L;
    private static final long USER_RESPONSE_ID1 = 20L;
    private static final long USER_RESPONSE_ID2 = 21L;
    private static final long USER_RESPONSE_ID3 = 22L;
    private static final long USER_RESPONSE_ID4 = 23L;
    private static final long QUESTION_ID1 = 30L;
    private static final long QUESTION_ID2 = 31L;
    private static final long QUESTION_ID3 = 32L;
    private static final long QUESTION_ID4 = 33L;
    private static final String QUESTION_LABEL1 = "QUESTION_LABEL1";
    private static final String QUESTION_LABEL2 = "QUESTION_LABEL2";
    private static final String QUESTION_LABEL3 = "QUESTION_LABEL3";
    private static final String QUESTION_LABEL4 = "QUESTION_LABEL4";
    private static final String USER_RESPONSE1 = "USER_RESPONSE1";
    private static final String USER_RESPONSE2 = "USER_RESPONSE2";
    private static final String USER_RESPONSE3 = "USER_RESPONSE3";
    private static final String USER_RESPONSE4 = "USER_RESPONSE4";
    private static final String SUBJECT_TITLE1 = "SUBJECT_TITLE1";
    private static final String SUBJECT_TITLE2 = "SUBJECT_TITLE2";

    @Mock
    private UserResponse userResponseMock1;
    @Mock
    private UserResponse userResponseMock2;
    @Mock
    private UserResponse userResponseMock3;
    @Mock
    private UserResponse userResponseMock4;
    @Mock
    private Question questionMock1;
    @Mock
    private Question questionMock2;
    @Mock
    private Question questionMock3;
    @Mock
    private Question questionMock4;
    @Mock
    private Subject subjectMock1;
    @Mock
    private Subject subjectMock2;
    @Mock
    private SurveyResponseDto surveyResponseDtoMock1;
    @Mock
    private SurveyResponseDto surveyResponseDtoMock2;

    private final SurveyMapper testInstance = new SurveyMapper();

    @Test
    void shouldConvertToSurveyResponse(){
        Map<Long, SurveyResponseDto> responsesAggr = Map.of(SUBJECT_ID2, surveyResponseDtoMock2, SUBJECT_ID1, surveyResponseDtoMock1);
        when(surveyResponseDtoMock1.getSubjectId()).thenReturn(SUBJECT_ID1);
        when(surveyResponseDtoMock2.getSubjectId()).thenReturn(SUBJECT_ID2);

        SurveyResponsesDto result = testInstance.toSurveyResponse(USER_ID, responsesAggr);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(2, result.getResponses().size());
        assertEquals(SUBJECT_ID1, result.getResponses().get(0).getSubjectId());
        assertEquals(surveyResponseDtoMock1, result.getResponses().get(0));
        assertEquals(SUBJECT_ID2, result.getResponses().get(1).getSubjectId());
        assertEquals(surveyResponseDtoMock2, result.getResponses().get(1));

    }

    @Test
    void shouldAggregateResponsesBySurveyIdIfEmpty() {
        List<UserResponse> userResponses = Collections.emptyList();

        Map<Long, SurveyResponseDto> result = testInstance.aggregateResponsesBySurveyId(userResponses);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldAggregateResponsesBySurveyId() {
        initResponses();
        List<UserResponse> userResponses = List.of(userResponseMock4, userResponseMock3, userResponseMock2,
            userResponseMock1);

        Map<Long, SurveyResponseDto> result = testInstance.aggregateResponsesBySurveyId(userResponses);

        assertEquals(2, result.size());
        assertEquals(SUBJECT_ID1, result.get(SUBJECT_ID1).getSubjectId());
        assertEquals(SUBJECT_TITLE1, result.get(SUBJECT_ID1).getSurveyTitle());
        assertEquals(SUBJECT_ID2, result.get(SUBJECT_ID2).getSubjectId());
        assertEquals(SUBJECT_TITLE2, result.get(SUBJECT_ID2).getSurveyTitle());

        assertEquals(2, result.get(SUBJECT_ID1).getQuestions().size());
        assertEquals(USER_RESPONSE_ID1, result.get(SUBJECT_ID1).getQuestions().get(0).getUserResponseId());
        assertEquals(QUESTION_ID1, result.get(SUBJECT_ID1).getQuestions().get(0).getQuestionId());
        assertEquals(QUESTION_LABEL1, result.get(SUBJECT_ID1).getQuestions().get(0).getUserResponseOption());
        assertEquals(USER_RESPONSE1, result.get(SUBJECT_ID1).getQuestions().get(0).getUserResponse());

        assertEquals(USER_RESPONSE_ID2, result.get(SUBJECT_ID1).getQuestions().get(1).getUserResponseId());
        assertEquals(QUESTION_ID2, result.get(SUBJECT_ID1).getQuestions().get(1).getQuestionId());
        assertEquals(QUESTION_LABEL2, result.get(SUBJECT_ID1).getQuestions().get(1).getUserResponseOption());
        assertEquals(USER_RESPONSE2, result.get(SUBJECT_ID1).getQuestions().get(1).getUserResponse());

        assertEquals(2, result.get(SUBJECT_ID2).getQuestions().size());
        assertEquals(USER_RESPONSE_ID3, result.get(SUBJECT_ID2).getQuestions().get(0).getUserResponseId());
        assertEquals(QUESTION_ID3, result.get(SUBJECT_ID2).getQuestions().get(0).getQuestionId());
        assertEquals(QUESTION_LABEL3, result.get(SUBJECT_ID2).getQuestions().get(0).getUserResponseOption());
        assertEquals(USER_RESPONSE3, result.get(SUBJECT_ID2).getQuestions().get(0).getUserResponse());

        assertEquals(USER_RESPONSE_ID4, result.get(SUBJECT_ID2).getQuestions().get(1).getUserResponseId());
        assertEquals(QUESTION_ID4, result.get(SUBJECT_ID2).getQuestions().get(1).getQuestionId());
        assertEquals(QUESTION_LABEL4, result.get(SUBJECT_ID2).getQuestions().get(1).getUserResponseOption());
        assertEquals(USER_RESPONSE4, result.get(SUBJECT_ID2).getQuestions().get(1).getUserResponse());
    }

    private void initResponses() {
        when(userResponseMock1.getId()).thenReturn(USER_RESPONSE_ID1);
        when(userResponseMock2.getId()).thenReturn(USER_RESPONSE_ID2);
        when(userResponseMock3.getId()).thenReturn(USER_RESPONSE_ID3);
        when(userResponseMock4.getId()).thenReturn(USER_RESPONSE_ID4);
        when(userResponseMock1.getContent()).thenReturn(USER_RESPONSE1);
        when(userResponseMock2.getContent()).thenReturn(USER_RESPONSE2);
        when(userResponseMock3.getContent()).thenReturn(USER_RESPONSE3);
        when(userResponseMock4.getContent()).thenReturn(USER_RESPONSE4);
        when(questionMock1.getId()).thenReturn(QUESTION_ID1);
        when(questionMock2.getId()).thenReturn(QUESTION_ID2);
        when(questionMock3.getId()).thenReturn(QUESTION_ID3);
        when(questionMock4.getId()).thenReturn(QUESTION_ID4);
        when(questionMock1.getLabel()).thenReturn(QUESTION_LABEL1);
        when(questionMock2.getLabel()).thenReturn(QUESTION_LABEL2);
        when(questionMock3.getLabel()).thenReturn(QUESTION_LABEL3);
        when(questionMock4.getLabel()).thenReturn(QUESTION_LABEL4);
        when(userResponseMock1.getQuestion()).thenReturn(questionMock1);
        when(userResponseMock2.getQuestion()).thenReturn(questionMock2);
        when(userResponseMock3.getQuestion()).thenReturn(questionMock3);
        when(userResponseMock4.getQuestion()).thenReturn(questionMock4);
        when(questionMock1.getSubject()).thenReturn(subjectMock1);
        when(questionMock2.getSubject()).thenReturn(subjectMock1);
        when(questionMock3.getSubject()).thenReturn(subjectMock2);
        when(questionMock4.getSubject()).thenReturn(subjectMock2);
        when(subjectMock1.getSubjectLabel()).thenReturn(SUBJECT_TITLE1);
        when(subjectMock2.getSubjectLabel()).thenReturn(SUBJECT_TITLE2);
        when(subjectMock1.getId()).thenReturn(SUBJECT_ID1);
        when(subjectMock2.getId()).thenReturn(SUBJECT_ID2);
    }
}