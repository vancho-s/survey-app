package com.survey.app.service.validator.impl;

import com.survey.app.exception.QuestionAnsweredException;
import com.survey.app.exception.QuestionNotFoundException;
import com.survey.app.repository.QuestionRepository;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.validator.QuestionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionValidatorImplTest {
    private static final long QUESTION_ID1 = 10L;
    private static final long QUESTION_ID2 = 11L;
    private static final long USER_ID = 1L;

    @Mock
    private QuestionRepository questionRepositoryMock;
    @Mock
    private UserResponseRepository userResponseRepositoryMock;

    private QuestionValidator testInstance;

    @BeforeEach
    void init() {
        testInstance = new QuestionValidatorImpl(questionRepositoryMock, userResponseRepositoryMock);
    }

    @Test
    void shouldVerifyQuestionsExistThrowException(){
        Set<Long> createQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        Set<Long> existingQuestionIds = Set.of(QUESTION_ID1);
        when(questionRepositoryMock.findExistingQuestionsIdsByIds(createQuestionIds)).thenReturn(existingQuestionIds);

        QuestionNotFoundException thrown = assertThrows(QuestionNotFoundException.class,
            () -> testInstance.verifyQuestionsExist(createQuestionIds));

        assertEquals(1, thrown.getNotFoundQuestionIds().size());
        assertTrue(thrown.getNotFoundQuestionIds().contains(QUESTION_ID2));
    }

    @Test
    void shouldVerifyQuestionsExistNotThrowException(){
        Set<Long> createQuestionIds = Collections.emptySet();

        assertDoesNotThrow(() -> testInstance.verifyQuestionsExist(createQuestionIds));
    }

    @Test
    void shouldVerifyIfAnsweredQuestionsAvailableThrowException(){
        Set<Long> createQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        Set<Long> existingQuestionIds = Set.of(QUESTION_ID1);
        when(userResponseRepositoryMock.findUserAnsweredResponses(USER_ID, createQuestionIds))
            .thenReturn(existingQuestionIds);

        QuestionAnsweredException thrown = assertThrows(QuestionAnsweredException.class,
            () -> testInstance.verifyIfAnsweredResponsesAvailable(USER_ID, createQuestionIds));

        assertEquals(1, thrown.getExistingQuestionIds().size());
        assertTrue(thrown.getExistingQuestionIds().contains(QUESTION_ID1));
    }

    @Test
    void shouldVerifyIfAnsweredQuestionsAvailableNotThrowException(){
        Set<Long> createQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        Set<Long> existingQuestionIds = Collections.emptySet();
        when(userResponseRepositoryMock.findUserAnsweredResponses(USER_ID, createQuestionIds))
            .thenReturn(existingQuestionIds);

        assertDoesNotThrow(() -> testInstance.verifyIfAnsweredResponsesAvailable(USER_ID, createQuestionIds));
    }

    @Test
    void shouldVerifyResponsesExistsByQuestionIdsThrowException() {
        Set<Long> createQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        Set<Long> existingQuestionIds = Set.of(QUESTION_ID1);
        when(userResponseRepositoryMock.findUserAnsweredResponses(USER_ID, createQuestionIds))
            .thenReturn(existingQuestionIds);

        QuestionNotFoundException thrown = assertThrows(QuestionNotFoundException.class,
            () -> testInstance.verifyResponsesExistsByQuestionIds(USER_ID, createQuestionIds));

        assertEquals(1, thrown.getNotFoundQuestionIds().size());
        assertTrue(thrown.getNotFoundQuestionIds().contains(QUESTION_ID2));
    }

    @Test
    void shouldVerifyResponsesExistsByQuestionIdsNotThrowException() {
        Set<Long> createQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        Set<Long> existingQuestionIds = Set.of(QUESTION_ID1, QUESTION_ID2);
        when(userResponseRepositoryMock.findUserAnsweredResponses(USER_ID, createQuestionIds))
            .thenReturn(existingQuestionIds);

        assertDoesNotThrow(() -> testInstance.verifyResponsesExistsByQuestionIds(USER_ID, createQuestionIds));
    }
}
