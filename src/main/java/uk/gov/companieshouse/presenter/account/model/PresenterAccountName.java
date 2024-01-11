package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.mongodb.core.mapping.Field;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public record PresenterAccountName(
        @Field("forename") String forename,
        @Field("surname") String surname) {

    private static final int MAX_FORENAME_LENGTH = 32;
    private static final int MAX_SURNAME_LENGTH = 40;

    public PresenterAccountName(final String forename, final String surname) {
        this.forename = validateName(forename, MAX_FORENAME_LENGTH);
        this.surname = validateName(surname, MAX_SURNAME_LENGTH);
    }

    private String validateName(final String name, final int maxLength) {
        if (StringValidator.validateString(name, maxLength)) {
            return name;
        } else {
            throw new ValidationException("Invalid Name");
        }
    }

    @Override
    public String toString() {
        return "PresenterName []";
    }

}
