package com.survey.app.web.error;

import io.swagger.annotations.ApiModel;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A representation for the rest fields errors list
 */
@ApiModel("RestFieldsErrorsDto")
public class RestFieldsErrorsDto extends RestErrorDto {

    private List<RestFieldErrorDto> fieldsErrors;

    /**
     * Constructor for the rest fields errors entity
     */
    public RestFieldsErrorsDto(final String code, final String message) {
        super(code, message);
        fieldsErrors = new ArrayList<>();
    }

    /**
     * Add a rest field error to the list of fields errors
     *
     * @param error error to add to the list
     */
    public void addError(final RestFieldErrorDto error) {
        Assert.notNull(error, "Cannot add a null error to the list of fields errors");

        if (fieldsErrors == null) {
            fieldsErrors = new ArrayList<>();
        }

        fieldsErrors.add(error);
    }

    /**
     * Get the list of the fields errors
     *
     * @return list of fields errors
     */
    public List<RestFieldErrorDto> getFieldsErrors() {
        return fieldsErrors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RestFieldsErrorsDto that = (RestFieldsErrorsDto) o;
        return Objects.equals(fieldsErrors, that.fieldsErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fieldsErrors);
    }
}
