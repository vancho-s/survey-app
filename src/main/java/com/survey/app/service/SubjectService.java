package com.survey.app.service;

import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    /**
     * @return Returns existing subjects by subjectIds
     * @param subjectIds
     */
    List<SubjectCreateResponseDto> findSubjectsByIds(final Set<Long> subjectIds);


    /**
     * Updates existing question for existing subject
     *
     * @param subject user request which contains a new subject to be created
     * @return a new subject
     */
    SubjectCreateResponseDto createSubject(final SubjectCreateDto subject);

}
