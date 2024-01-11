package uk.gov.companieshouse.presenter.account.validation.utils;

import java.util.regex.Pattern;

public final class StringValidator {

    private static final String VALID_CHARACTERS = "-,.:; 0-9A-Z&@$£¥€'\"«»?!/\\\\()\\[\\]{}<>*="
            + "#%+ÀÁÂÃÄÅĀĂĄÆǼÇĆĈĊČÞĎÐÈÉÊËĒĔĖĘĚĜĞĠĢĤĦÌÍÎÏĨĪĬĮİĴĶĹĻĽĿŁÑŃŅŇŊÒÓÔÕÖØŌŎŐǾŒŔŖŘ"
            + "ŚŜŞŠŢŤŦÙÚÛÜŨŪŬŮŰŲŴẀẂẄỲÝŶŸŹŻŽa-zſƒǺàáâãäåāăąæǽçćĉċčþďðèéêëēĕėęěĝģğġĥħ"
            + "ìíîïĩīĭįĵķĺļľŀłñńņňŋòóôõöøōŏőǿœŕŗřśŝşšţťŧùúûüũūŭůűųŵẁẃẅỳýŷÿźżž";

    private static final String UUID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    /**
     * 
     * @param unvalidatedString the raw string to be validated
     * @param maxLength         of a valid string
     * @return is string matches the requirements of being a valid string and length
     *         is up to and including maxLength
     */
    public static final boolean validateString(final String unvalidatedString, final int maxLength) {

        if (unvalidatedString != null && !unvalidatedString.isBlank()) {
            final String fullRegex = regexForValidString(maxLength);

            if (Pattern.matches(fullRegex, unvalidatedString)) {
                return true;
            }
        }

        return false;
    }

    public static final boolean validateUUID(final String id) throws IllegalArgumentException {
        return id != null && Pattern.matches(UUID_REGEX, id);
    }

    /**
     * 
     * @param maxLength max matching length.
     * @return regex of VALID_CHARACTERS from 1 to maxLength
     */
    private static final String regexForValidString(final int maxLength) {
        return "^[" + VALID_CHARACTERS + "]{1," + maxLength + "}$";
    }

    private StringValidator() {
    }
}
