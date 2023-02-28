package com.survey.app.service.impl;

import com.survey.app.domain.Subject;
import com.survey.app.repository.SubjectRepository;
import com.survey.app.service.SubjectService;
import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;
import com.survey.app.service.mapper.SubjectMapper;
import com.survey.app.service.validator.SubjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {
    private static final long SUBJECT_ID = 10L;

    @Mock
    private SubjectRepository subjectRepositoryMock;
    @Mock
    private SubjectMapper subjectMapperMock;
    @Mock
    private SubjectValidator subjectValidatorMock;
    @Mock
    private Subject subjectMock;
    @Mock
    private Subject createdSubjectMock;
    @Mock
    private SubjectCreateResponseDto subjectCreateResponseDtoMock;
    @Mock
    private SubjectCreateDto subjectDtoMock;

    private SubjectService testInstance;

    @BeforeEach
    void init() {
        testInstance = new SubjectServiceImpl(subjectRepositoryMock, subjectMapperMock, subjectValidatorMock);
    }

    @Test
    void shouldFindSubjectsAndQuestionsBuSubjectIds() {
        Set<Subject> subjects = Set.of(subjectMock);
        Set<Long> subjectIds = Set.of(SUBJECT_ID);
        when(subjectRepositoryMock.findSubjectsByIds(subjectIds)).thenReturn(subjects);
        List<SubjectCreateResponseDto> subjectCreateResponseDtos = List.of(subjectCreateResponseDtoMock);
        when(subjectMapperMock.toSubjectDtoList(subjects)).thenReturn(subjectCreateResponseDtos);

        List<SubjectCreateResponseDto> result = testInstance.findSubjectsByIds(subjectIds);
        assertFalse(result.isEmpty());
        assertEquals(subjectCreateResponseDtoMock, result.get(0));
        verify(subjectValidatorMock).verifySubjectsExist(subjectIds);
        verify(subjectRepositoryMock).findSubjectsByIds(subjectIds);
        verify(subjectMapperMock).toSubjectDtoList(subjects);
    }

    @Test
    void shouldCreateSubject() {
        when(subjectMapperMock.toSubject(subjectDtoMock)).thenReturn(subjectMock);
        when(subjectRepositoryMock.save(subjectMock)).thenReturn(createdSubjectMock);
        when(subjectMapperMock.subjectToDto(createdSubjectMock)).thenReturn(subjectCreateResponseDtoMock);

        SubjectCreateResponseDto result = testInstance.createSubject(subjectDtoMock);

        assertEquals(subjectCreateResponseDtoMock, result);
        verify(subjectMapperMock).toSubject(subjectDtoMock);
        verify(subjectRepositoryMock).save(subjectMock);
        verify(subjectMapperMock).subjectToDto(createdSubjectMock);
    }
}
