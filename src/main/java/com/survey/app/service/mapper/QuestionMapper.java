package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.Subject;
import com.survey.app.service.dto.QuestionCreateDto;
import com.survey.app.service.dto.QuestionResponse;
import com.survey.app.service.dto.QuestionUpdateDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class QuestionMapper {
    public QuestionResponse toQuestionCreateUpdateResponse(final long subjectId, final Collection<Question> questions) {
        QuestionResponse response = new QuestionResponse();
        response.setSubjectId(subjectId);
        List<QuestionCreateDto> questionsDto = questions.stream().sorted(comparing(Question::getId))
            .map(q -> new QuestionCreateDto(q.getId(), q.getLabel(), q.getEnabled()))
            .collect(Collectors.toList());
        response.setSubjectQuestions(questionsDto);
        return response;
    }

    public List<QuestionCreateDto> toQuestionCreateDtoList(final Collection<Question> questions) {
        return questions.stream().sorted(comparing(Question::getId))
            .map(q -> new QuestionCreateDto(q.getId(), q.getLabel(), q.getEnabled()))
            .collect(Collectors.toList());
    }

    public List<Question> questionDtosToQuestions(final long subjectId,
                                                  final List<QuestionCreateDto> questionCreateDtos) {
        return questionCreateDtos.stream()
            .map(q -> new Question(null, q.getQuestionLabel(), new Subject(subjectId), q.getEnabled()))
            .collect(Collectors.toList());
    }

    public Set<Long> questionUpdateDtoListToQuestionIds(final List<QuestionUpdateDto> questionDtos) {
        return questionDtos.stream().map(QuestionUpdateDto::getQuestionId).collect(Collectors.toSet());
    }
}
