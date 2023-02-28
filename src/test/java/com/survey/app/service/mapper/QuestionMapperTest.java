package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.Subject;
import com.survey.app.service.dto.QuestionCreateDto;
import com.survey.app.service.dto.QuestionResponse;
import com.survey.app.service.dto.QuestionUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionMapperTest {
    private static final long SUBJECT_ID = 10L;
    private static final long QUESTION1_ID = 20L;
    private static final String QUESTION_LABEL1 = "QUESTION_LABEL1";
    private static final long QUESTION2_ID = 21L;
    private static final String QUESTION_LABEL2 = "QUESTION_LABEL2";

    @Mock
    private Question questionMock1;
    @Mock
    private Question questionMock2;
    @Mock
    private Subject subjectMock;
    @Mock
    private QuestionCreateDto questionCreateDtoMock;
    @Mock
    private QuestionUpdateDto questionUpdateDtoMock;

    private final QuestionMapper testInstance = new QuestionMapper();


    @Test
    void shouldConvertToQuestionCreateUpdateResponseGroupQuestionsByIds() {
        List<Question> questionList = getQuestions();

        QuestionResponse result = testInstance.toQuestionCreateUpdateResponse(SUBJECT_ID, questionList);

        assertEquals(SUBJECT_ID, result.getSubjectId());
        assertEquals(2, result.getSubjectQuestions().size());
        assertEquals(QUESTION1_ID, result.getSubjectQuestions().get(0).getQuestionId());
        assertEquals(QUESTION_LABEL1, result.getSubjectQuestions().get(0).getQuestionLabel());
        assertTrue(result.getSubjectQuestions().get(0).getEnabled());
        assertEquals(QUESTION2_ID, result.getSubjectQuestions().get(1).getQuestionId());
        assertEquals(QUESTION_LABEL2, result.getSubjectQuestions().get(1).getQuestionLabel());
        assertTrue(result.getSubjectQuestions().get(1).getEnabled());
    }

    @Test
    void shouldConvertToQuestionCreateDtoList() {
        List<Question> questionList = getQuestions();

        List<QuestionCreateDto> result = testInstance.toQuestionCreateDtoList(questionList);
        assertEquals(2, result.size());
        assertEquals(QUESTION1_ID, result.get(0).getQuestionId());
        assertEquals(QUESTION_LABEL1, result.get(0).getQuestionLabel());
        assertTrue(result.get(0).getEnabled());
        assertEquals(QUESTION2_ID, result.get(1).getQuestionId());
        assertEquals(QUESTION_LABEL2, result.get(1).getQuestionLabel());
        assertTrue(result.get(1).getEnabled());
    }

    @Test
    void shouldConvertQuestionDtosToQuestions() {
        List<QuestionCreateDto> questionCreateDtos = List.of(questionCreateDtoMock);
        when(questionCreateDtoMock.getQuestionLabel()).thenReturn(QUESTION_LABEL1);
        when(questionCreateDtoMock.getEnabled()).thenReturn(true);

        List<Question> result = testInstance.questionDtosToQuestions(SUBJECT_ID, questionCreateDtos);

        assertFalse(result.isEmpty());
        assertEquals(QUESTION_LABEL1, result.get(0).getLabel());
        assertEquals(SUBJECT_ID, result.get(0).getSubject().getId());
        assertTrue(result.get(0).getEnabled());
    }

    @Test
    void shouldConvertQuestionUpdateDtoListToQuestionIds() {
        List<QuestionUpdateDto> questionUpdateDtoList = List.of(questionUpdateDtoMock);
        when(questionUpdateDtoMock.getQuestionId()).thenReturn(QUESTION1_ID);

        Set<Long> result = testInstance.questionUpdateDtoListToQuestionIds(questionUpdateDtoList);

        assertEquals(1, result.size());
        assertTrue(result.contains(QUESTION1_ID));
    }

    private List<Question> getQuestions() {
        List<Question> questionList = List.of(questionMock2, questionMock1);
        when(questionMock1.getId()).thenReturn(QUESTION1_ID);
        when(questionMock1.getLabel()).thenReturn(QUESTION_LABEL1);
        when(questionMock1.getEnabled()).thenReturn(true);
        when(questionMock2.getId()).thenReturn(QUESTION2_ID);
        when(questionMock2.getLabel()).thenReturn(QUESTION_LABEL2);
        when(questionMock2.getEnabled()).thenReturn(true);
        return questionList;
    }
}
