package com.survey.app.service.validator.impl;

import com.survey.app.exception.QuestionAnsweredException;
import com.survey.app.exception.QuestionNotFoundException;
import com.survey.app.repository.QuestionRepository;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.validator.QuestionValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class QuestionValidatorImpl implements QuestionValidator {
    private final QuestionRepository questionRepository;
    private final UserResponseRepository userResponseRepository;

    public QuestionValidatorImpl(final QuestionRepository questionRepository,
                                 final UserResponseRepository userResponseRepository) {
        this.questionRepository = questionRepository;
        this.userResponseRepository = userResponseRepository;
    }

    @Override
    public void verifyQuestionsExist(final Set<Long> createQuestionIds) {
        Set<Long> createQuestionIdsCopy = new HashSet<>(createQuestionIds);
        if (!isEmpty(createQuestionIdsCopy)) {
            Set<Long> existingQuestionIds = questionRepository.findExistingQuestionsIdsByIds(createQuestionIdsCopy);
            if (createQuestionIdsCopy.size() != existingQuestionIds.size()) {
                createQuestionIdsCopy.removeAll(existingQuestionIds);
                throw new QuestionNotFoundException(createQuestionIdsCopy);
            }
        }
    }

    @Override
    public void verifyIfAnsweredResponsesAvailable(final long userId, final Set<Long> createQuestionIds) {
        if (!isEmpty(createQuestionIds)) {
            Set<Long> answeredQuestionIds = userResponseRepository.findUserAnsweredResponses(userId, createQuestionIds);
            if (!isEmpty(answeredQuestionIds)) {
                throw new QuestionAnsweredException(answeredQuestionIds);
            }
        }
    }

    @Override
    public void verifyResponsesExistsByQuestionIds(final long userId, final Set<Long> updateQuestionIds) {
        Set<Long> updateQuestionIdsCopy = new HashSet<>(updateQuestionIds);
        if (!isEmpty(updateQuestionIdsCopy)) {
            Set<Long> existingQuestionIds = userResponseRepository
                .findUserAnsweredResponses(userId, updateQuestionIdsCopy);
            if (updateQuestionIdsCopy.size() != existingQuestionIds.size()) {
                updateQuestionIdsCopy.removeAll(existingQuestionIds);
                throw new QuestionNotFoundException(updateQuestionIdsCopy);
            }
        }
    }
}
