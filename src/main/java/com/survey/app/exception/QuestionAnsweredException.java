package com.survey.app.exception;

import java.util.Set;

public class QuestionAnsweredException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Questions with ids %s already answered";
    private final Set<Long> existingQuestionIds;

    public QuestionAnsweredException(Set<Long> existingQuestionIds) {
        super(String.format(ERROR_MESSAGE, existingQuestionIds));
        this.existingQuestionIds = existingQuestionIds;
    }

    public Set<Long> getExistingQuestionIds() {
        return existingQuestionIds;
    }
}
