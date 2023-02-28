package com.survey.app.service;

import com.survey.app.service.dto.SurveyResponsesDto;

import java.util.Set;

public interface SurveyService {

    /**
     * Find all surveys for user by userId and subjectIds
     *
     * @param userId id of the user who responded
     * @param subjectIds list of subjects
     * @return list of surveys
     * @throws com.survey.app.exception.UserNotFoundException if no user was found
     * @throws com.survey.app.exception.SubjectNotFoundException if survey not found
     */
    SurveyResponsesDto findAllSurveysByUserIdAndSubjectIds(final long userId, final Set<Long> subjectIds);

    /**
     * Find all surveys for user by userId
     *
     * @param userId id of the user who responded
     * @return list of surveys
     * @throws com.survey.app.exception.UserNotFoundException if no user was found
     */
    SurveyResponsesDto findAllSurveysByUserId(final long userId);
}
