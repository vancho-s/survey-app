package com.survey.app.service.impl;

import com.survey.app.domain.UserResponse;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.UserResponseService;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponseDto;
import com.survey.app.service.dto.UserResponsesResponse;
import com.survey.app.service.mapper.UserResponseMapper;
import com.survey.app.service.validator.QuestionValidator;
import com.survey.app.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;


@Service
@Transactional
public class UserResponseServiceImpl implements UserResponseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResponseServiceImpl.class);
    private final UserValidator userValidator;
    private final QuestionValidator questionValidator;
    private final UserResponseRepository userResponseRepository;
    private final UserResponseMapper userResponseMapper;

    public UserResponseServiceImpl(final UserValidator userValidator, final QuestionValidator questionValidator,
                                   final UserResponseRepository userResponseRepository,
                                   final UserResponseMapper userResponseMapper) {
        this.userValidator = userValidator;
        this.questionValidator = questionValidator;
        this.userResponseRepository = userResponseRepository;
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    public UserResponsesResponse createUserResponses(final UserResponsesRequest userResponsesRequest) {
        LOGGER.debug("Saving user createdResponses for user: {}", userResponsesRequest.getUserId());
        if (CollectionUtils.isEmpty(userResponsesRequest.getUserResponses())) {
            LOGGER.debug("No user createdResponses to save");
            return userResponseMapper.userResponseToDtoList(userResponsesRequest.getUserId(), Collections.emptyList());
        }
        userValidator.verifyUserExist(userResponsesRequest.getUserId());
        Set<Long> existingQuestionIds = userResponseMapper
            .userResponsesToIdsList(userResponsesRequest.getUserResponses());
        questionValidator.verifyQuestionsExist(existingQuestionIds);
        questionValidator.verifyIfAnsweredResponsesAvailable(userResponsesRequest.getUserId(), existingQuestionIds);
        List<UserResponse> createdResponses = userResponseRepository
            .saveAll(userResponseMapper.responsesRequestToResponsesList(userResponsesRequest));
        LOGGER.debug("Created createdResponses {} for user: {}", createdResponses.size(), userResponsesRequest.getUserId());
        return userResponseMapper.userResponseToDtoList(userResponsesRequest.getUserId(), createdResponses);
    }

    @Override
    public UserResponsesResponse updateUserResponses(final UserResponsesRequest userResponsesRequest) {
        LOGGER.debug("Updating user responses for user: {}", userResponsesRequest.getUserId());
        if (CollectionUtils.isEmpty(userResponsesRequest.getUserResponses())) {
            LOGGER.debug("No user responses to update");
            return userResponseMapper.userResponseToDtoList(userResponsesRequest.getUserId(), Collections.emptyList());
        }
        userValidator.verifyUserExist(userResponsesRequest.getUserId());
        Set<Long> questionIds = userResponseMapper
            .userResponsesToIdsList(userResponsesRequest.getUserResponses());
        questionValidator.verifyResponsesExistsByQuestionIds(userResponsesRequest.getUserId(), questionIds);
        List<UserResponse> userResponsesExisting = userResponseRepository
            .findUserResponsesByUserIdAndQuestionIds(userResponsesRequest.getUserId(), questionIds);
        Map<Long, List<UserResponseDto>> userUpdateResponsesAgr = getUpdateResponseArg(userResponsesRequest);
        updateExistingResponses(userUpdateResponsesAgr, userResponsesExisting);
        List<UserResponse> userResponsesUpdated = userResponseRepository.saveAll(userResponsesExisting);
        LOGGER.debug("Updated responses {} for user: {}", userResponsesUpdated.size(),
            userResponsesRequest.getUserId());
        return userResponseMapper.userResponseToDtoList(userResponsesRequest.getUserId(), userResponsesUpdated);
    }

    private Map<Long, List<UserResponseDto>> getUpdateResponseArg(final UserResponsesRequest userResponsesRequest) {
        return userResponsesRequest.getUserResponses().stream()
                .collect(groupingBy(UserResponseDto::getQuestionId));
    }

    private void updateExistingResponses(final Map<Long, List<UserResponseDto>> userUpdateResponsesAgr,
                                         final List<UserResponse> userResponsesExisting) {
        for (UserResponse userResponse : userResponsesExisting) {
            List<UserResponseDto> dtoList = userUpdateResponsesAgr.get(userResponse.getQuestion().getId());
            userResponse.setContent(dtoList.get(0).getUserResponse());
        }
    }
}
