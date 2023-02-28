package com.survey.app.exception;

import java.util.Set;

public class SubjectNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Subjects with ids %s were not found";
    private final Set<Long> notFoundSubjectIds;

    public SubjectNotFoundException(final Set<Long> notFoundSubjectIds) {
        super(String.format(ERROR_MESSAGE, notFoundSubjectIds));
        this.notFoundSubjectIds = notFoundSubjectIds;
    }

    public Set<Long> getNotFoundSubjectIds() {
        return notFoundSubjectIds;
    }
}
