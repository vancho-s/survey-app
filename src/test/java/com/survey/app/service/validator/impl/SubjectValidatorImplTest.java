package com.survey.app.service.validator.impl;

import com.survey.app.domain.Subject;
import com.survey.app.exception.SubjectNotFoundException;
import com.survey.app.exception.SurveyNotFoundException;
import com.survey.app.repository.SubjectRepository;
import com.survey.app.service.validator.SubjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectValidatorImplTest {
    private static final long SUBJECT_ID1 = 10L;
    private static final long SUBJECT_ID2 = 11L;

    @Mock
    private SubjectRepository subjectRepositoryMock;
    @Mock
    private Subject subjectMock;

    private SubjectValidator testInstance;

    @BeforeEach
    void init() {
        testInstance = new SubjectValidatorImpl(subjectRepositoryMock);
    }

    @Test
    void shouldVerifySubjectsExistThrowException(){
        Set<Long> subjectIds = Set.of(SUBJECT_ID1, SUBJECT_ID2);
        Set<Long> existingSubjectsIds = Set.of(SUBJECT_ID1);
        when(subjectRepositoryMock.findSubjectIdsByIds(subjectIds)).thenReturn(existingSubjectsIds);

        SubjectNotFoundException thrown = assertThrows(SubjectNotFoundException.class,
            () -> testInstance.verifySubjectsExist(subjectIds));

        assertEquals(1, thrown.getNotFoundSubjectIds().size());
        assertTrue(thrown.getNotFoundSubjectIds().contains(SUBJECT_ID2));
    }

    @Test
    void shouldVerifySubjectsExistNotThrowException(){
        Set<Long> subjectIds = Set.of(SUBJECT_ID1, SUBJECT_ID2);
        Set<Long> existingSubjectsIds = Set.of(SUBJECT_ID1, SUBJECT_ID2);
        when(subjectRepositoryMock.findSubjectIdsByIds(subjectIds)).thenReturn(existingSubjectsIds);

        assertDoesNotThrow(() -> testInstance.verifySubjectsExist(subjectIds));
    }

    @Test
    void shouldVerifySurveysWithSubjectsExistThrowException(){
        Set<Long> subjectIds = Set.of(SUBJECT_ID1, SUBJECT_ID2);
        Set<Long> existingSubjectsIds = Set.of(SUBJECT_ID1);

        SurveyNotFoundException thrown = assertThrows(SurveyNotFoundException.class,
            () -> testInstance.verifyResponsesForSubjectsExist(existingSubjectsIds, subjectIds));

        assertEquals(1, thrown.getNotFoundSurveyIds().size());
        assertTrue(thrown.getNotFoundSurveyIds().contains(SUBJECT_ID2));
    }
}
