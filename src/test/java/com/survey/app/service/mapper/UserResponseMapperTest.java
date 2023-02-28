package com.survey.app.service.mapper;

import com.survey.app.domain.Question;
import com.survey.app.domain.UserResponse;
import com.survey.app.service.dto.UserResponseDto;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponsesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResponseMapperTest {
    private static final long USER_ID = 1L;
    private static final long USER_RESPONSE_ID1 = 30L;
    private static final String USER_RESPONSE = "USER_RESPONSE";
    private static final long QUESTION_ID = 10L;
    private static final String QUESTION_LABEL = "QUESTION_LABEL";
    private static final String RESPONSE_CONTENT = "RESPONSE_CONTENT";

    @Mock
    private UserResponsesRequest userResponsesRequestMock;
    @Mock
    private UserResponseDto userResponseDtoMock;
    @Mock
    private UserResponse userResponseMock;
    @Mock
    private Question questionMock;

    private final UserResponseMapper testInstance = new UserResponseMapper();

    @Test
    void shouldConvertResponsesRequestToResponsesList() {
        List<UserResponseDto> userResponseDtoList = List.of(userResponseDtoMock);
        when(userResponsesRequestMock.getUserResponses()).thenReturn(userResponseDtoList);
        when(userResponsesRequestMock.getUserId()).thenReturn(USER_ID);
        when(userResponseDtoMock.getUserResponse()).thenReturn(USER_RESPONSE);
        when(userResponseDtoMock.getQuestionId()).thenReturn(QUESTION_ID);

        List<UserResponse> result = testInstance.responsesRequestToResponsesList(userResponsesRequestMock);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(USER_RESPONSE, result.get(0).getContent());
        assertEquals(QUESTION_ID, result.get(0).getQuestion().getId());
        assertEquals(USER_ID, result.get(0).getUser().getId());
    }

    @Test
    void shouldConvertUserResponsesToIdsList() {
        List<UserResponseDto> userResponseDtoList = List.of(userResponseDtoMock);
        when(userResponseDtoMock.getQuestionId()).thenReturn(QUESTION_ID);

        Set<Long> result = testInstance.userResponsesToIdsList(userResponseDtoList);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.contains(QUESTION_ID));
    }

    @Test
    void shouldConvertUserResponseToDtoList() {
        List<UserResponse> userResponses = List.of(userResponseMock);
        when(userResponseMock.getId()).thenReturn(USER_RESPONSE_ID1);
        when(userResponseMock.getQuestion()).thenReturn(questionMock);
        when(userResponseMock.getContent()).thenReturn(RESPONSE_CONTENT);
        when(questionMock.getId()).thenReturn(QUESTION_ID);
        when(questionMock.getLabel()).thenReturn(QUESTION_LABEL);

        UserResponsesResponse result = testInstance.userResponseToDtoList(USER_ID, userResponses);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(1, result.getResponses().size());
        assertEquals(USER_RESPONSE_ID1, result.getResponses().get(0).getUserResponseId());
        assertEquals(QUESTION_ID, result.getResponses().get(0).getQuestionId());
        assertEquals(QUESTION_LABEL, result.getResponses().get(0).getUserResponseOption());
        assertEquals(RESPONSE_CONTENT, result.getResponses().get(0).getUserResponse());
    }
}
