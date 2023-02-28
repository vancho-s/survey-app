package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.Subject;
import com.survey.app.service.dto.QuestionCreateDto;
import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectMapperTest {
    private static final String SUBJECT_LABEL1 = "SUBJECT_LABEL1";
    private static final String SUBJECT_LABEL2 = "SUBJECT_LABEL2";
    private static final long SUBJECT1_ID = 1L;
    private static final long SUBJECT2_ID = 2L;

    @Mock
    private QuestionMapper questionMapperMock;
    @Mock
    private SubjectCreateDto subjectCreateDtoMock;
    @Mock
    private Subject subjectMock1;
    @Mock
    private Subject subjectMock2;
    @Mock
    private QuestionCreateDto questionCreateDtoMock;
    @Mock
    private Question questionMock;

    @InjectMocks
    private SubjectMapper testInstance;

    @Test
    void shouldConvertToSubject(){
        when(subjectCreateDtoMock.getSubjectLabel()).thenReturn(SUBJECT_LABEL1);

        Subject result = testInstance.toSubject(subjectCreateDtoMock);

        assertEquals(SUBJECT_LABEL1, result.getSubjectLabel());
    }

    @Test
    void shouldConvertToSubjectIfNull(){
        Subject result = testInstance.toSubject(subjectCreateDtoMock);

        assertNull(result.getSubjectLabel());
    }

    @Test
    void shouldConvertSubjectToDto() {
        when(subjectMock1.getId()).thenReturn(SUBJECT1_ID);
        when(subjectMock1.getSubjectLabel()).thenReturn(SUBJECT_LABEL1);

        SubjectCreateResponseDto result = testInstance.subjectToDto(subjectMock1);

        assertEquals(SUBJECT1_ID, result.getSubjectId());
        assertEquals(SUBJECT_LABEL1, result.getSubjectLabel());
    }

    @Test
    void shouldConvertSubjectToDtoIfNull() {
        SubjectCreateResponseDto result = testInstance.subjectToDto(subjectMock1);

        assertEquals(0, result.getSubjectId());
        assertNull(result.getSubjectLabel());
    }

    @Test
    void shouldConvertToSubjectDtoList(){
        Set<Subject> subjectSet = Set.of(subjectMock2, subjectMock1);
        when(subjectMock1.getId()).thenReturn(SUBJECT1_ID);
        when(subjectMock1.getSubjectLabel()).thenReturn(SUBJECT_LABEL1);
        Set<Question> questions = Set.of(questionMock);
        when(subjectMock1.getQuestions()).thenReturn(questions);
        when(subjectMock2.getId()).thenReturn(SUBJECT2_ID);
        when(subjectMock2.getSubjectLabel()).thenReturn(SUBJECT_LABEL2);
        when(subjectMock2.getQuestions()).thenReturn(questions);
        List<QuestionCreateDto> questionCreateDtoList = List.of(questionCreateDtoMock);
        when(questionMapperMock.toQuestionCreateDtoList(questions)).thenReturn(questionCreateDtoList);

        List<SubjectCreateResponseDto> result = testInstance.toSubjectDtoList(subjectSet);

        assertEquals(2, result.size());
        assertEquals(SUBJECT1_ID, result.get(0).getSubjectId());
        assertEquals(SUBJECT_LABEL1, result.get(0).getSubjectLabel());
        assertEquals(questionCreateDtoMock, result.get(0).getQuestions().get(0));
        assertEquals(SUBJECT2_ID, result.get(1).getSubjectId());
        assertEquals(SUBJECT_LABEL2, result.get(1).getSubjectLabel());
        assertEquals(questionCreateDtoMock, result.get(1).getQuestions().get(0));
    }
}
