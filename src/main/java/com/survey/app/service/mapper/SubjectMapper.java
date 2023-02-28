package com.survey.app.service.mapper;

import com.survey.app.domain.Subject;
import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class SubjectMapper {
    @Autowired
    private QuestionMapper questionMapper;

    public Subject toSubject(final SubjectCreateDto subjectCreateDto) {
        Subject subject = new Subject();
        if (subjectCreateDto != null) {
            subject.setSubjectLabel(subjectCreateDto.getSubjectLabel());
        }
        return subject;
    }

    public SubjectCreateResponseDto subjectToDto(final Subject subject) {
        SubjectCreateResponseDto subjectCreateResponseDto = new SubjectCreateResponseDto();
        if (subject != null) {
            subjectCreateResponseDto.setSubjectId(subject.getId());
            subjectCreateResponseDto.setSubjectLabel(subject.getSubjectLabel());
        }
        return subjectCreateResponseDto;
    }

    public List<SubjectCreateResponseDto> toSubjectDtoList(final Set<Subject> subjects) {
        return subjects.stream().sorted(comparing(Subject::getId))
            .map(s -> new SubjectCreateResponseDto(s.getId(), s.getSubjectLabel(),
                questionMapper.toQuestionCreateDtoList(s.getQuestions()))).collect(Collectors.toList());
    }
}
