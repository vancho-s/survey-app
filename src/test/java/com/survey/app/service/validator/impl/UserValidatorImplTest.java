package com.survey.app.service.validator.impl;

import com.survey.app.exception.UserNotFoundException;
import com.survey.app.repository.UserRepository;
import com.survey.app.service.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {
    private static final long USER_ID = 1L;

    @Mock
    private UserRepository userRepositoryMock;

    private UserValidator testInstance;

    @BeforeEach
    void init() {
        testInstance = new UserValidatorImpl(userRepositoryMock);
    }

    @Test
    void shouldVerifyUserExistThrowException() {
        List<Long> userIds = List.of(USER_ID);
        when(userRepositoryMock.findExistingUsersByIds(userIds)).thenReturn(Collections.emptyList());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
            () -> testInstance.verifyUserExist(USER_ID));

        assertEquals(USER_ID, thrown.getNotFoundUsersId());
    }

    @Test
    void shouldVerifyUserExistNotThrowException() {
        List<Long> userIds = List.of(USER_ID);
        when(userRepositoryMock.findExistingUsersByIds(userIds)).thenReturn(List.of(USER_ID));

        assertDoesNotThrow(() -> testInstance.verifyUserExist(USER_ID));
    }
}
