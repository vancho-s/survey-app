package com.survey.app.service.validator.impl;

import com.survey.app.exception.UserNotFoundException;
import com.survey.app.repository.UserRepository;
import com.survey.app.service.validator.UserValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.Collections.singletonList;

@Component
public class UserValidatorImpl implements UserValidator {
    private final UserRepository userRepository;

    public UserValidatorImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void verifyUserExist(final long userId) {
        List<Long> existingUsersIds = userRepository.findExistingUsersByIds(singletonList(userId));
        if (CollectionUtils.isEmpty(existingUsersIds)) {
            throw new UserNotFoundException(userId);
        }
    }
}
