package com.survey.app.service.validator.impl;

import com.survey.app.exception.SubjectNotFoundException;
import com.survey.app.exception.SurveyNotFoundException;
import com.survey.app.repository.SubjectRepository;
import com.survey.app.service.validator.SubjectValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Component
public class SubjectValidatorImpl implements SubjectValidator {
    private final SubjectRepository subjectRepository;

    public SubjectValidatorImpl(final SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void verifySubjectsExist(final Set<Long> subjectIds) {
        Set<Long> subjectIdsCopy = new HashSet<>(subjectIds);
        if (!CollectionUtils.isEmpty(subjectIdsCopy)) {
            Set<Long> existingSubjectsIds = subjectRepository.findSubjectIdsByIds(subjectIdsCopy);
            if (existingSubjectsIds.size() != subjectIdsCopy.size()) {
                subjectIdsCopy.removeAll(existingSubjectsIds);
                throw new SubjectNotFoundException(subjectIdsCopy);
            }
        }
    }

    @Override
    public void verifyResponsesForSubjectsExist(final Set<Long> existingSubjectIds, final Set<Long> subjectIds) {
        Set<Long> subjectIdsCopy = new HashSet<>(subjectIds);
        if (existingSubjectIds.size() != subjectIdsCopy.size()) {
            subjectIdsCopy.removeAll(existingSubjectIds);
            throw new SurveyNotFoundException(subjectIdsCopy);
        }
    }
}
