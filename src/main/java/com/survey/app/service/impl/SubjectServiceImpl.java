package com.survey.app.service.impl;

import com.survey.app.domain.Subject;
import com.survey.app.repository.SubjectRepository;
import com.survey.app.service.SubjectService;
import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;
import com.survey.app.service.mapper.SubjectMapper;
import com.survey.app.service.validator.SubjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final SubjectValidator subjectValidator;

    public SubjectServiceImpl(final SubjectRepository subjectRepository, final SubjectMapper subjectMapper,
                              final SubjectValidator subjectValidator) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.subjectValidator = subjectValidator;
    }

    @Override
    public List<SubjectCreateResponseDto> findSubjectsByIds(final Set<Long> subjectIds) {
        LOGGER.debug("Return existing subjects and related questions by subjectIds");
        subjectValidator.verifySubjectsExist(subjectIds);
        Set<Subject> subjects = subjectRepository.findSubjectsByIds(subjectIds);
        LOGGER.debug("Found subjects: {}", subjects.size());
        return subjectMapper.toSubjectDtoList(subjects);
    }

    @Override
    public SubjectCreateResponseDto createSubject(final SubjectCreateDto subjectCreateDto) {
        LOGGER.debug("Request to create a new subject: {}", subjectCreateDto.getSubjectLabel());
        Subject subject = subjectRepository.save(subjectMapper.toSubject(subjectCreateDto));
        LOGGER.debug("Subject: {} was created", subject.getId());
        return subjectMapper.subjectToDto(subject);
    }
}
