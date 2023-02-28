package com.survey.app.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "User with ids %s was not found";
    private final long notFoundUserId;

    public UserNotFoundException(long notFoundUserId) {
        super(String.format(ERROR_MESSAGE, notFoundUserId));
        this.notFoundUserId = notFoundUserId;
    }

    public long getNotFoundUsersId() {
        return notFoundUserId;
    }
}
