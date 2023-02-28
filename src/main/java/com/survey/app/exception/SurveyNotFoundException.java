package com.survey.app.exception;

import java.util.Set;

public class SurveyNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Surveys with ids %s were not found";
    private final Set<Long> notFoundSurveyIds;

    public SurveyNotFoundException(final Set<Long> notFoundSurveyIds) {
        super(String.format(ERROR_MESSAGE, notFoundSurveyIds));
        this.notFoundSurveyIds = notFoundSurveyIds;
    }

    public Set<Long> getNotFoundSurveyIds() {
        return notFoundSurveyIds;
    }
}
