package com.survey.app.service.validator;

import java.util.Set;

public interface SubjectValidator {

    void verifySubjectsExist(final Set<Long> subjectIds);

    void verifyResponsesForSubjectsExist(final Set<Long> existingSubjectIds, final Set<Long> subjectIds);

}
