package com.survey.app.service.impl;

import com.survey.app.domain.Question;
import com.survey.app.domain.Subject;
import com.survey.app.repository.QuestionRepository;
import com.survey.app.service.QuestionService;
import com.survey.app.service.dto.*;
import com.survey.app.service.mapper.QuestionMapper;
import com.survey.app.service.validator.QuestionValidator;
import com.survey.app.service.validator.SubjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    private static final long SUBJECT_ID = 1L;
    private static final long QUESTION_ID = 10L;
    private static final String QUESTION_LABEL = "QUESTION_LABEL";

    @Mock
    private SubjectValidator subjectValidatorMock;
    @Mock
    private QuestionMapper questionMapperMock;
    @Mock
    private QuestionValidator questionValidatorMock;
    @Mock
    private QuestionRepository questionRepositoryMock;
    @Mock
    private QuestionCreateRequest questionCreateRequestMock;
    @Mock
    private QuestionUpdateRequest questionUpdateRequestMock;
    @Mock
    private QuestionResponse questionResponseMock;
    @Mock
    private QuestionCreateDto questionCreateDtoMock;
    @Mock
    private QuestionUpdateDto questionUpdateDtoMock;
    @Mock
    private Subject subjectMock;
    @Mock
    private Question questionMock;
    @Mock
    private Question updatedQuestionMock;

    private QuestionService testInstance;

    @BeforeEach
    void init() {
        testInstance = new QuestionServiceImpl(subjectValidatorMock, questionMapperMock, questionValidatorMock, questionRepositoryMock);
    }

    @Test
    void shouldAddQuestionsToSubjectWithEmptyQuestions() {
        List<QuestionCreateDto> subjectQuestions = Collections.emptyList();
        when(questionCreateRequestMock.getSubjectId()).thenReturn(SUBJECT_ID);
        when(questionCreateRequestMock.getSubjectQuestions()).thenReturn(subjectQuestions);
        when(questionMapperMock.toQuestionCreateUpdateResponse(anyLong(), anyCollection()))
            .thenReturn(questionResponseMock);

        QuestionResponse result = testInstance.createSubjectQuestions(questionCreateRequestMock);

        assertTrue(result.getSubjectQuestions().isEmpty());
        verify(questionMapperMock).toQuestionCreateUpdateResponse(anyLong(), anyCollection());
    }

    @Test
    void shouldAddQuestionsToSubject() {
        List<QuestionCreateDto> subjectQuestions = List.of(questionCreateDtoMock);
        when(questionCreateRequestMock.getSubjectId()).thenReturn(SUBJECT_ID);
        when(questionCreateRequestMock.getSubjectQuestions()).thenReturn(subjectQuestions);
        Set<Long> subjectIds = Set.of(SUBJECT_ID);
        List<Question> questions = List.of(questionMock);
        when(questionMapperMock.questionDtosToQuestions(SUBJECT_ID, subjectQuestions)).thenReturn(questions);
        List<Question> savedQuestions = List.of(questionMock);
        when(questionRepositoryMock.saveAll(questions)).thenReturn(savedQuestions);
        List<QuestionCreateDto> questionCreateDtoList = List.of(questionCreateDtoMock);
        when(questionResponseMock.getSubjectId()).thenReturn(SUBJECT_ID);
        when(questionResponseMock.getSubjectQuestions()).thenReturn(questionCreateDtoList);
        when(questionMapperMock.toQuestionCreateUpdateResponse(SUBJECT_ID, savedQuestions))
            .thenReturn(questionResponseMock);

        QuestionResponse result = testInstance.createSubjectQuestions(questionCreateRequestMock);

        assertFalse(result.getSubjectQuestions().isEmpty());
        assertEquals(questionCreateDtoMock, result.getSubjectQuestions().get(0));
        assertEquals(SUBJECT_ID, result.getSubjectId());
        verify(subjectValidatorMock).verifySubjectsExist(subjectIds);
        verify(questionMapperMock).questionDtosToQuestions(SUBJECT_ID, subjectQuestions);
        verify(questionRepositoryMock).saveAll(questions);
        verify(questionMapperMock).toQuestionCreateUpdateResponse(SUBJECT_ID, savedQuestions);
    }

    @Test
    void shouldNotUpdateQuestionsToSubjectWithEmptyQuestions() {
        List<QuestionUpdateDto> subjectQuestions = Collections.emptyList();
        when(questionUpdateRequestMock.getSubjectId()).thenReturn(SUBJECT_ID);
        when(questionUpdateRequestMock.getSubjectQuestions()).thenReturn(subjectQuestions);
        when(questionMapperMock.toQuestionCreateUpdateResponse(anyLong(), anyCollection()))
            .thenReturn(questionResponseMock);

        QuestionResponse result = testInstance.updateSubjectQuestions(questionUpdateRequestMock);

        assertTrue(result.getSubjectQuestions().isEmpty());
        verify(questionMapperMock).toQuestionCreateUpdateResponse(anyLong(), anyCollection());
    }

    @Test
    void shouldUpdateQuestionsToSubject() {
        List<QuestionUpdateDto> subjectQuestions = List.of(questionUpdateDtoMock);
        when(questionUpdateDtoMock.getQuestionId()).thenReturn(QUESTION_ID);
        when(questionUpdateDtoMock.getEnabled()).thenReturn(true);
        when(questionUpdateDtoMock.getQuestionLabel()).thenReturn(QUESTION_LABEL);
        when(questionUpdateRequestMock.getSubjectId()).thenReturn(SUBJECT_ID);
        when(questionUpdateRequestMock.getSubjectQuestions()).thenReturn(subjectQuestions);
        Set<Long> subjectIds = Set.of(SUBJECT_ID);
        Set<Long> existingQuestionIds = Set.of(QUESTION_ID);
        when(questionMapperMock.questionUpdateDtoListToQuestionIds(subjectQuestions)).thenReturn(existingQuestionIds);
        Set<Question> existingQuestions = Set.of(questionMock);
        when(questionMock.getId()).thenReturn(QUESTION_ID);
        when(questionRepositoryMock.findExistingQuestionsByIds(existingQuestionIds)).thenReturn(existingQuestions);
        List<Question> updatedQuestions = List.of(updatedQuestionMock);
        when(questionRepositoryMock.saveAll(existingQuestions)).thenReturn(updatedQuestions);
        List<QuestionCreateDto> questionCreateDtos = List.of(questionCreateDtoMock);
        when(questionResponseMock.getSubjectQuestions()).thenReturn(questionCreateDtos);
        when(questionMapperMock.toQuestionCreateUpdateResponse(SUBJECT_ID, updatedQuestions))
            .thenReturn(questionResponseMock);

        QuestionResponse result = testInstance.updateSubjectQuestions(questionUpdateRequestMock);

        assertFalse(result.getSubjectQuestions().isEmpty());
        assertEquals(questionCreateDtoMock, result.getSubjectQuestions().get(0));
        verify(subjectValidatorMock).verifySubjectsExist(subjectIds);
        verify(questionMapperMock).questionUpdateDtoListToQuestionIds(subjectQuestions);
        verify(questionRepositoryMock).findExistingQuestionsByIds(existingQuestionIds);
        verify(questionMock).setLabel(QUESTION_LABEL);
        verify(questionMock).setEnabled(true);
        verify(questionRepositoryMock).saveAll(existingQuestions);
        verify(questionMapperMock).toQuestionCreateUpdateResponse(SUBJECT_ID, updatedQuestions);
    }
}
