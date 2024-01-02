package uk.gov.companieshouse.presenter.account.validation.utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;


public final class StringValidator {

    private static final int MAX_REGEX_LENGTH = 200;

    private static final String VALID_CHARACTERS = "-,.:; 0-9A-Z&@$£¥€'\"«»?!/\\\\()\\[\\]{}<>*=#%+ÀÁÂÃÄÅĀĂĄÆǼÇĆĈĊČÞĎÐÈÉÊËĒĔĖĘĚĜĞĠĢĤĦÌÍÎÏĨĪĬĮİĴĶĹĻĽĿŁÑŃŅŇŊÒÓÔÕÖØŌŎŐǾŒŔŖŘŚŜŞŠŢŤŦÙÚÛÜŨŪŬŮŰŲŴẀẂẄỲÝŶŸŹŻŽa-zſƒǺàáâãäåāăąæǽçćĉċčþďðèéêëēĕėęěĝģğġĥħìíîïĩīĭįĵķĺļľŀłñńņňŋòóôõöøōŏőǿœŕŗřśŝşšţťŧùúûüũūŭůűųŵẁẃẅỳýŷÿźżž";

    /**
     * 
     * @param unvalidatedString the raw string to be validated
     * @param maxLength of a valid string
     * @return Optional of string that passed validation or an empty optional if not.
     * @throws IllegalArgumentException if an invalid max length is entered.
     */
    public static final Optional<String> validateString(final String unvalidatedString, final int maxLength) throws IllegalArgumentException {
        boolean validLength = MAX_REGEX_LENGTH >= maxLength && maxLength > 0;
        boolean validToParse = unvalidatedString != null && !unvalidatedString.isBlank();

        if(validLength && validToParse){
            final String unvalidStringUTF8 = new String(unvalidatedString.getBytes(), StandardCharsets.UTF_8);
            final String fullRegex = regexForValidString(maxLength);

            if (Pattern.matches(fullRegex, unvalidStringUTF8) && unvalidStringUTF8.length() <= maxLength){
                return Optional.of(unvalidStringUTF8);
            }
        }

        // This is a misuse of method check.
        if(!validLength){
            final String additionalInfo = String.format("String was %d when it should be between 1 and %d", maxLength, MAX_REGEX_LENGTH);
            throw new IllegalArgumentException(additionalInfo);
        }

        return Optional.empty();
    }

    /**
     * 
     * @param maxLength max matching length.
     * @return regex of VALID_CHARACTERS from 1 to maxLength
     */
    private static final String regexForValidString(final int maxLength) {
        return "^[" + VALID_CHARACTERS + "]{1," + maxLength + "}$";
    }

    private StringValidator(){
    }
}
