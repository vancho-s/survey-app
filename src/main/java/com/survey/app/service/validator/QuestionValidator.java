package com.survey.app.service.validator;

import java.util.Set;

public interface QuestionValidator {

    void verifyQuestionsExist(Set<Long> createQuestionIds);

    void verifyIfAnsweredResponsesAvailable(final long userId, final Set<Long> createQuestionIds);

    void verifyResponsesExistsByQuestionIds(final long userId, final Set<Long> updateQuestionIds);

}
