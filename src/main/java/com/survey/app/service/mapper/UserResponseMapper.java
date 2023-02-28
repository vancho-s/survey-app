package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.User;
import com.survey.app.domain.UserResponse;
import com.survey.app.service.dto.UserResponseDto;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponsesResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class UserResponseMapper {
    public List<UserResponse> responsesRequestToResponsesList(final UserResponsesRequest userResponsesRequest) {
        return userResponsesRequest.getUserResponses().stream()
            .map(urDto -> new UserResponse(null, urDto.getUserResponse(),
            new Question(urDto.getQuestionId()), new User(userResponsesRequest.getUserId())))
            .collect(toList());
    }

    public Set<Long> userResponsesToIdsList(final List<UserResponseDto> userResponses) {
        return userResponses.stream().map(UserResponseDto::getQuestionId).collect(toSet());
    }

    public UserResponsesResponse userResponseToDtoList(final long userId, final List<UserResponse> responses) {
        List<UserResponseDto> userResponseDtoList = responses.stream()
            .map(ur -> new UserResponseDto(ur.getId(), ur.getQuestion().getId(), ur.getQuestion().getLabel(),
                ur.getContent())).collect(toList());
        UserResponsesResponse response = new UserResponsesResponse();
        response.setUserId(userId);
        response.setResponses(userResponseDtoList);
        return response;
    }
}
