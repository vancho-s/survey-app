package com.survey.app.web.error;

import com.survey.app.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class RestExceptionTranslator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionTranslator.class);

    /**
     * Handle UserNotFoundException
     *
     * @return 404 status with message telling which users not found
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseBody
    public RestErrorDto handleUserNotFoundException(final UserNotFoundException exception) {
        LOGGER.error("Users ids were not found: " + exception.getNotFoundUsersId(), exception);
        return new RestErrorDto(HttpStatus.NOT_FOUND.toString(), exception.getMessage());
    }

    /**
     * Handle QuestionAnsweredException
     *
     * @return 409 status with message telling which questions already answered
     */
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(value = QuestionAnsweredException.class)
    @ResponseBody
    public RestErrorDto handleQuestionAnsweredException(final QuestionAnsweredException exception) {
        LOGGER.error("Questions ids already exist: " + exception.getExistingQuestionIds(), exception);
        return new RestErrorDto(HttpStatus.CONFLICT.toString(), exception.getMessage());
    }

    /**
     * Handle QuestionNotFoundException
     *
     * @return 404 status with message telling which questions was not found
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = QuestionNotFoundException.class)
    @ResponseBody
    public RestErrorDto handleQuestionNotFoundException(final QuestionNotFoundException exception) {
        LOGGER.error("Question ids were not found: " + exception.getNotFoundQuestionIds(), exception);
        return new RestErrorDto(HttpStatus.NOT_FOUND.toString(), exception.getMessage());
    }

    /**
     * Handle SubjectNotFoundException
     *
     * @return 404 status with message telling which subjects was not found
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = SubjectNotFoundException.class)
    @ResponseBody
    public RestErrorDto handleSubjectNotFoundException(final SubjectNotFoundException exception) {
        LOGGER.error("Subject ids were not found: " + exception.getNotFoundSubjectIds(), exception);
        return new RestErrorDto(HttpStatus.NOT_FOUND.toString(), exception.getMessage());
    }

    /**
     * Handle SurveyNotFoundException
     *
     * @return 404 status with message telling which surveys was not found
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = SurveyNotFoundException.class)
    @ResponseBody
    public RestErrorDto handleSurveyNotFoundException(final SurveyNotFoundException exception) {
        LOGGER.error("Subject ids were not found: " + exception.getNotFoundSurveyIds(), exception);
        return new RestErrorDto(HttpStatus.NOT_FOUND.toString(), exception.getMessage());
    }

    /**
     * Handle HttpMessageNotReadableException
     *
     * @return 404 status with message telling with user payload parsing issue details
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public RestErrorDto handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        LOGGER.error("Can not parse message", exception);
        return new RestErrorDto(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }

    /**
     * Handle validation errors
     *
     * @return validation error with the fields errors and a bad request
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public RestFieldsErrorsDto handleValidationExceptions(final MethodArgumentNotValidException exception) {
        LOGGER.error(exception.getMessage(), exception);

        BindingResult result = exception.getBindingResult();
        RestFieldsErrorsDto restFieldsErrors = new RestFieldsErrorsDto(HttpStatus.BAD_REQUEST.toString(),
            "Request " + "content is invalid, error: " + HttpStatus.BAD_REQUEST);

        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(fieldError -> restFieldsErrors.addError(new RestFieldErrorDto(fieldError.getField(),
            fieldError.getCode(), "Request content is invalid, error: " + fieldError.getDefaultMessage())));

        return restFieldsErrors;

    }

    /**
     * Handle all types of errors
     *
     * @return internal server error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestErrorDto handleAllExceptions(final Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return new RestErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
    }
}
