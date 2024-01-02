package uk.gov.companieshouse.presenter.account.validation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
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
    private static final int NEGATIVE_LENGTH = -10024;
    private static final int OVER_LIMIT_LENGTH = 201;
    private static final int UNDER_LIMIT_LENGTH = 0;
    private static final int BIG_LENGTH = Integer.MAX_VALUE;
    private static final int NEGATIVE_BIG_LENGTH = Integer.MIN_VALUE;


    @ParameterizedTest
    @ValueSource(strings = {NORMAL_STRING})
    @DisplayName("Pass string Validation Format")
    void testStringValidationFormatPass(String STRING) {
        Optional<String> returnString = StringValidator.validateString(STRING, LENGTH);
        assertEquals(STRING, returnString.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {LENGTH, MAX_LENGTH, MIN_LENGTH})
    @DisplayName("Pass String Validation Length")
    void testStringValidationLengthPass(int length) {
        Optional<String> returnString = StringValidator.validateString(SHORTEST_VALID_STRING, length);
        assertEquals(SHORTEST_VALID_STRING, returnString.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {NEGATIVE_LENGTH, OVER_LIMIT_LENGTH, UNDER_LIMIT_LENGTH, BIG_LENGTH, NEGATIVE_BIG_LENGTH})
    @DisplayName("Failed string Validation Length")
    void testStringValidationLengthFail(int length) {
        assertThrows(IllegalArgumentException.class, () -> StringValidator.validateString(NORMAL_STRING, length));
    }

    @ParameterizedTest
    @ValueSource(strings = {NO_STRING, INVALID_CHARACTER_STRING, MULTILINE_STRING })
    @DisplayName("Failed string Validation incorrect format")
    void testStringValidationFailed(String STRING) {
        Optional<String> returnString = StringValidator.validateString(STRING, LENGTH);
        assertTrue(returnString.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {TOO_LONG_STRING, NO_STRING})
    @DisplayName("Failed string Validation STRING string not right length")
    void testStringWrongStringLengthFailed(String STRING){
        Optional<String> returnString = StringValidator.validateString(STRING, LENGTH);
        assertTrue(returnString.isEmpty());
    }

    @Test
    @DisplayName("Failed string Validation normal length string with lowest length limit")
    void testStringLowLimitWithSTRINGFailed() {
        Optional<String> returnString = StringValidator.validateString(NORMAL_STRING, MIN_LENGTH);
        assertTrue(returnString.isEmpty());
    }
}
