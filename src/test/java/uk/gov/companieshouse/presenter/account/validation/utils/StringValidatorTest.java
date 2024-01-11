package uk.gov.companieshouse.presenter.account.validation.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringValidatorTest {

    private static final String NORMAL_STRING = "normal string";
    private static final String NO_STRING = "";
    private static final String TOO_LONG_STRING = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String INVALID_CHARACTER_STRING = "~";
    private static final String SHORTEST_VALID_STRING = "a";
    private static final String MULTILINE_STRING = """
            a
            a
            """;

    private static final int LENGTH = 40;
    private static final int MAX_LENGTH = 200;
    private static final int MIN_LENGTH = 1;
    private static final int BIG_LENGTH = Integer.MAX_VALUE;

    private static final String VALID_UUID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private static final String INVALID_UUID = "Invalid";

    @ParameterizedTest
    @ValueSource(strings = { NORMAL_STRING })
    @DisplayName("Pass string Validation Format")
    void testStringValidationFormatPass(String STRING) {
        assertTrue(StringValidator.validateString(STRING, LENGTH));
    }

    @ParameterizedTest
    @ValueSource(ints = { LENGTH, MAX_LENGTH, MIN_LENGTH, BIG_LENGTH })
    @DisplayName("Pass String Validation Length")
    void testStringValidationLengthPass(int length) {
        assertTrue(StringValidator.validateString(SHORTEST_VALID_STRING, length));
    }

    @ParameterizedTest
    @ValueSource(strings = { NO_STRING, INVALID_CHARACTER_STRING, MULTILINE_STRING })
    @DisplayName("Failed string Validation incorrect format")
    void testStringValidationFailed(String STRING) {
        assertFalse(StringValidator.validateString(STRING, LENGTH));
    }

    @ParameterizedTest
    @ValueSource(strings = { TOO_LONG_STRING, NO_STRING })
    @DisplayName("Failed string Validation STRING string not right length")
    void testStringWrongStringLengthFailed(String STRING) {
        assertFalse(StringValidator.validateString(STRING, LENGTH));
    }

    @Test
    @DisplayName("Failed string Validation normal length string with lowest length limit")
    void testStringLowLimitWithSTRINGFailed() {
        assertFalse(StringValidator.validateString(NORMAL_STRING, MIN_LENGTH));
    }

    @Test
    @DisplayName("Pass uuid string with valid string uuid")
    void testValidateUUID() {
        assertTrue(StringValidator.validateUUID(VALID_UUID));
    }

    @Test
    @DisplayName("Failed uuid string with invalid input")
    void testInvalidValidateUUID() {
        assertFalse(StringValidator.validateUUID(null));
        assertFalse(StringValidator.validateUUID(INVALID_UUID));
    }
}
