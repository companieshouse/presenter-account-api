package uk.gov.companieshouse.presenter.account.validation.utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

public final class EmailValidator {

    private static final int MAX_LENGTH = 80;

    private static final int MIN_MAX_LENGTH = 5;

    private static final String VALID_CHARACTERS = "-,.:; 0-9A-Z&@$£¥€'\"«»?!/\\\\()\\[\\]{}<>*="
            + "#%+ÀÁÂÃÄÅĀĂĄÆǼÇĆĈĊČÞĎÐÈÉÊËĒĔĖĘĚĜĞĠĢĤĦÌÍÎÏĨĪĬĮİĴĶĹĻĽĿŁÑŃŅŇŊÒÓÔÕÖØŌŎŐǾŒŔŖŘ"
            + "ŚŜŞŠŢŤŦÙÚÛÜŨŪŬŮŰŲŴẀẂẄỲÝŶŸŹŻŽa-zſƒǺàáâãäåāăąæǽçćĉċčþďðèéêëēĕėęěĝģğġĥħ"
            + "ìíîïĩīĭįĵķĺļľŀłñńņňŋòóôõöøōŏőǿœŕŗřśŝşšţťŧùúûüũūŭůűųŵẁẃẅỳýŷÿźżž";

    private static final String EMAIL_FORMAT = "^[" + VALID_CHARACTERS + "]+@[" + VALID_CHARACTERS + "]+\\.["
            + VALID_CHARACTERS + "]+$";

    /**
     * 
     * @param unvalidatedEmail the raw email string to be validated
     * @param maxLength        pf a valid email
     * @return Optional of email that passed validation or an empty optional if not.
     * @throws IllegalArgumentException if an invalid max length is entered.
     */
    public static final Optional<String> validateEmail(final String unvalidatedEmail, final int maxLength)
            throws IllegalArgumentException {
        boolean validLength = MAX_LENGTH >= maxLength && maxLength >= MIN_MAX_LENGTH;
        boolean validToParse = unvalidatedEmail != null && !unvalidatedEmail.isBlank();

        if (validLength && validToParse) {

            final String unvalidEmailUTF8 = new String(unvalidatedEmail.getBytes(), StandardCharsets.UTF_8);

            if (Pattern.matches(EMAIL_FORMAT, unvalidEmailUTF8)) {
                return Optional.of(unvalidEmailUTF8);
            }
        }

        // This is a misuse of method check.
        if (!validLength) {
            final String additionalInfo = String.format("Email was %d when it should be between %d and %d", maxLength,
                    MIN_MAX_LENGTH, MAX_LENGTH);
            throw new IllegalArgumentException(additionalInfo);
        }

        return Optional.empty();
    }

    private EmailValidator() {

    }
}
