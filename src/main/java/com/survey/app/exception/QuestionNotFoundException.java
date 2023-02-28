package com.survey.app.exception;

import java.util.Set;

public class QuestionNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Questions with ids %s were not found";
    private final Set<Long> notFoundQuestionIds;

    public QuestionNotFoundException(Set<Long> notFoundQuestionIds) {
        super(String.format(ERROR_MESSAGE, notFoundQuestionIds));
        this.notFoundQuestionIds = notFoundQuestionIds;
    }

    public Set<Long> getNotFoundQuestionIds() {
        return notFoundQuestionIds;
    }
}
