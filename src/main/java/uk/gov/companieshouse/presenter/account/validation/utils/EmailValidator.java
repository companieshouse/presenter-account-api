package uk.gov.companieshouse.presenter.account.validation.utils;

import java.util.regex.Pattern;

public final class EmailValidator {

    private static final String EMAIL_FORMAT = "^.+@.+\\..+$";

    /**
     * 
     * @param unvalidatedEmail the raw email string to be validated
     * @param maxLength        of a valid email
     * @return Optional of email that passed validation or an empty optional if not.
     */
    public static final boolean validateEmail(final String unvalidatedEmail, final int maxLength) {
        boolean validToParse = unvalidatedEmail != null && !unvalidatedEmail.isBlank();

        return validToParse && unvalidatedEmail.length() <= maxLength
                && Pattern.matches(EMAIL_FORMAT, unvalidatedEmail);

    }

    private EmailValidator() {

    }
}
