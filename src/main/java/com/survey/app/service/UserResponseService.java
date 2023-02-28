package com.survey.app.service;

import com.survey.app.exception.QuestionAnsweredException;
import com.survey.app.exception.QuestionNotFoundException;
import com.survey.app.exception.UserNotFoundException;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponsesResponse;

public interface UserResponseService {

    /**
     * Save user responses for provided questions ids
     *
     * @param userResponsesRequest contains user id and list of question responses
     * @return List of the saved responses of the user
     * @throws UserNotFoundException if no user was found
     * @throws QuestionNotFoundException if no question was found
     * @throws QuestionNotFoundException if no question was found
     * @throws QuestionAnsweredException if question was already answered
     */
    UserResponsesResponse createUserResponses(final UserResponsesRequest userResponsesRequest);

    /**
     * Updates user responses for provided questions ids
     *
     * @param userResponsesRequest contains user id and list of question responses
     * @return List of the updated responses of the user
     * @throws UserNotFoundException if no user was found
     * @throws QuestionNotFoundException if no question was found
     * @throws QuestionNotFoundException if no question was found
     */
    UserResponsesResponse updateUserResponses(final UserResponsesRequest userResponsesRequest);
}
