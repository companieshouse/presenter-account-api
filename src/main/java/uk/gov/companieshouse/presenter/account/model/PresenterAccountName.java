package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.mongodb.core.mapping.Field;

import org.springframework.lang.Nullable;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public record PresenterAccountName(
        @Field("forename") @Nullable String forename,
        @Field("surname") @Nullable String surname) {

    private static final int MAX_FORENAME_LENGTH = 32;
    private static final int MAX_SURNAME_LENGTH = 40;

    public PresenterAccountName(final String forename, final String surname) {
        this.forename = validateName(forename, MAX_FORENAME_LENGTH);
        this.surname = validateName(surname, MAX_SURNAME_LENGTH);
    }

    private String validateName(final String name, final int maxLength) {
        // CHS user profiles often don't have associated forename and surnames
        // Therefore null is a valid value for the name field.
        if (name == null || StringValidator.validateString(name, maxLength)) {
            return name;
        }

        throw new ValidationException("Invalid Name");
    }

    @Override
    public String toString() {
        return "PresenterName []";
    }

}
