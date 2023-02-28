package com.survey.app.service.impl;

import com.survey.app.domain.Question;
import com.survey.app.domain.UserResponse;
import com.survey.app.repository.UserResponseRepository;
import com.survey.app.service.UserResponseService;
import com.survey.app.service.dto.UserResponseDto;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponsesResponse;
import com.survey.app.service.mapper.UserResponseMapper;
import com.survey.app.service.validator.QuestionValidator;
import com.survey.app.service.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResponseServiceImplTest {
    private static final long USER_ID = 1L;
    private static final long RESPONSE_ID = 10L;
    private static final String USER_ANSWER = "USER_ANSWER";
    private static final long QUESTION_ID = 30L;

    @Mock
    private UserValidator userValidatorMock;
    @Mock
    private QuestionValidator questionValidatorMock;
    @Mock
    private UserResponseRepository userResponseRepositoryMock;
    @Mock
    private UserResponseMapper userResponseMapperMock;
    @Mock
    private UserResponsesRequest userResponsesRequestMock;
    @Mock
    private UserResponsesResponse userResponsesResponseMock;
    @Mock
    private UserResponse userResponseMock;
    @Mock
    private Question questionMock;
    @Mock
    private UserResponse createdUserResponseMock;
    @Mock
    private UserResponseDto userResponseDtoMock;


    private UserResponseService testInstance;

    @BeforeEach
    void init() {
        testInstance = new UserResponseServiceImpl(userValidatorMock, questionValidatorMock, userResponseRepositoryMock,
            userResponseMapperMock);
    }

    @Test
    void shouldCreateUserResponsesIfNoRequests(){
        List<UserResponse> responseDtoList = Collections.emptyList();
        when(userResponseMapperMock.userResponseToDtoList(USER_ID, responseDtoList))
            .thenReturn(userResponsesResponseMock);
        when(userResponsesRequestMock.getUserId()).thenReturn(USER_ID);

        UserResponsesResponse result = testInstance.createUserResponses(userResponsesRequestMock);

        assertTrue(result.getResponses().isEmpty());
        verify(userResponseMapperMock).userResponseToDtoList(USER_ID, responseDtoList);
    }

    @Test
    void shouldCreateUserResponses(){
        when(userResponsesRequestMock.getUserId()).thenReturn(USER_ID);
        List<UserResponseDto> userResponses = List.of(userResponseDtoMock);
        when(userResponsesRequestMock.getUserResponses()).thenReturn(userResponses);
        Set<Long> existingQuestionIds = Set.of(RESPONSE_ID);
        when(userResponseMapperMock.userResponsesToIdsList(userResponses)).thenReturn(existingQuestionIds);
        List<UserResponse> userResponsesList = List.of(userResponseMock);
        when(userResponseMapperMock.responsesRequestToResponsesList(userResponsesRequestMock))
            .thenReturn(userResponsesList);
        List<UserResponse> createdUserResponses = List.of(createdUserResponseMock);
        when(userResponseRepositoryMock.saveAll(userResponsesList)).thenReturn(createdUserResponses);
        when(userResponseMapperMock.userResponseToDtoList(USER_ID, createdUserResponses))
            .thenReturn(userResponsesResponseMock);
        List<UserResponseDto> userResponseDtoList = List.of(userResponseDtoMock);
        when(userResponsesResponseMock.getResponses()).thenReturn(userResponseDtoList);

        UserResponsesResponse result = testInstance.createUserResponses(userResponsesRequestMock);

        assertFalse(result.getResponses().isEmpty());
        assertEquals(userResponseDtoMock, result.getResponses().get(0));
        verify(userValidatorMock).verifyUserExist(USER_ID);
        verify(questionValidatorMock).verifyQuestionsExist(existingQuestionIds);
        verify(questionValidatorMock).verifyIfAnsweredResponsesAvailable(USER_ID, existingQuestionIds);
        verify(userResponseMapperMock).responsesRequestToResponsesList(userResponsesRequestMock);
        verify(userResponseRepositoryMock).saveAll(userResponsesList);
        verify(userResponseMapperMock).userResponseToDtoList(USER_ID, createdUserResponses);
    }

    @Test
    void shouldUpdateUserResponsesIfNoResponses(){
        List<UserResponse> responseDtoList = Collections.emptyList();
        when(userResponseMapperMock.userResponseToDtoList(USER_ID, responseDtoList))
            .thenReturn(userResponsesResponseMock);
        when(userResponsesRequestMock.getUserId()).thenReturn(USER_ID);

        UserResponsesResponse result = testInstance.updateUserResponses(userResponsesRequestMock);

        assertTrue(result.getResponses().isEmpty());
        verify(userResponseMapperMock).userResponseToDtoList(USER_ID, responseDtoList);
    }

    @Test
    void shouldUpdateUserResponses(){
        when(userResponsesRequestMock.getUserId()).thenReturn(USER_ID);
        List<UserResponseDto> userResponses = List.of(userResponseDtoMock);
        when(userResponseDtoMock.getQuestionId()).thenReturn(QUESTION_ID);
        when(userResponseDtoMock.getUserResponse()).thenReturn(USER_ANSWER);
        when(userResponsesRequestMock.getUserResponses()).thenReturn(userResponses);
        Set<Long> existingQuestionIds = Set.of(RESPONSE_ID);
        when(userResponseMapperMock.userResponsesToIdsList(userResponses)).thenReturn(existingQuestionIds);
        List<UserResponse> existingUserResponses = List.of(userResponseMock);
        when(userResponseMock.getQuestion()).thenReturn(questionMock);
        when(questionMock.getId()).thenReturn(QUESTION_ID);
        when(userResponseRepositoryMock.findUserResponsesByUserIdAndQuestionIds(USER_ID, existingQuestionIds))
            .thenReturn(existingUserResponses);
        List<UserResponse> updatedUserResponses = List.of(createdUserResponseMock);
        when(userResponseRepositoryMock.saveAll(existingUserResponses)).thenReturn(updatedUserResponses);
        when(userResponseMapperMock.userResponseToDtoList(USER_ID, updatedUserResponses))
            .thenReturn(userResponsesResponseMock);
        List<UserResponseDto> userResponseDtoList = List.of(userResponseDtoMock);
        when(userResponsesResponseMock.getUserId()).thenReturn(USER_ID);
        when(userResponsesResponseMock.getResponses()).thenReturn(userResponseDtoList);

        UserResponsesResponse result = testInstance.updateUserResponses(userResponsesRequestMock);

        assertFalse(result.getResponses().isEmpty());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(userResponseDtoMock, result.getResponses().get(0));
        assertEquals(QUESTION_ID, result.getResponses().get(0).getQuestionId());
        assertEquals(USER_ANSWER, result.getResponses().get(0).getUserResponse());
        verify(userValidatorMock).verifyUserExist(USER_ID);
        verify(userResponseMapperMock).userResponsesToIdsList(userResponses);
        verify(questionValidatorMock).verifyResponsesExistsByQuestionIds(USER_ID, existingQuestionIds);
        verify(userResponseMock).setContent(USER_ANSWER);
        verify(userResponseRepositoryMock).findUserResponsesByUserIdAndQuestionIds(USER_ID, existingQuestionIds);
        verify(userResponseRepositoryMock).saveAll(existingUserResponses);
        verify(userResponseMapperMock).userResponseToDtoList(USER_ID, updatedUserResponses);
    }

}
