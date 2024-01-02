package uk.gov.companieshouse.presenter.account.validation.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailValidatorTest {

    private static final String NO_DOT_EMAIL = "test@testtest";
    private static final String NO_AT_EMAIL = "testtest.test";
    private static final String NO_START_EMAIL = "@test.test";
    private static final String NO_EMAIL = "";
    private static final String NO_END_EMAIL = "test@test.";
    private static final String NO_DOMAIN_EMAIL = "test@.test";
    private static final String EMAIL = "test@test.test";
    private static final String INVALID_CHARACTER_EMAIL = "~test@test.test";
    private static final String SHORTEST_VALID_EMAIL = "a@a.a";
    private static final String MULTILINE_EMAIL = """
            a
            @
            a
            .
            a
            """;

    private static final int LENGTH = 80;
    private static final int MAX_LENGTH = 80;
    private static final int MIN_LENGTH = 5;
    private static final int NEGATIVE_LENGTH = -10024;
    private static final int UNDER_LIMIT_LENGTH = 4;
    private static final int BIG_LENGTH = Integer.MAX_VALUE;
    private static final int NEGATIVE_BIG_LENGTH = Integer.MIN_VALUE;

    @ParameterizedTest
    @ValueSource(strings = { EMAIL })
    @DisplayName("Pass Email Validation Format")
    void testEmailValidationFormatPass(String email) {
        assertTrue(EmailValidator.validateEmail(email, LENGTH));
    }

    @ParameterizedTest
    @ValueSource(ints = { LENGTH, MAX_LENGTH, MIN_LENGTH, BIG_LENGTH })
    @DisplayName("Pass Email Validation Length")
    void testEmailValidationLengthPass(int length) {
        assertTrue(EmailValidator.validateEmail(SHORTEST_VALID_EMAIL, length));
    }

    @ParameterizedTest
    @ValueSource(ints = { NEGATIVE_LENGTH, UNDER_LIMIT_LENGTH, NEGATIVE_BIG_LENGTH })
    @DisplayName("Failed Email Validation Length")
    void testEmailValidationLengthFail(int length) {
        assertFalse(EmailValidator.validateEmail(EMAIL, length));
    }

    @ParameterizedTest
    @ValueSource(strings = { NO_AT_EMAIL, NO_DOT_EMAIL, NO_START_EMAIL,
            NO_EMAIL, NO_END_EMAIL, NO_DOMAIN_EMAIL,
            INVALID_CHARACTER_EMAIL, MULTILINE_EMAIL })
    @DisplayName("Failed Email Validation incorrect format")
    void testEmailValidationFailed(String email) {
        assertFalse(EmailValidator.validateEmail(email, LENGTH));
    }

    @Test
    @DisplayName("Failed Email Validation normal length email with lowest length limit")
    void testEmailLowLimitWithEmailFailed() {
        assertFalse(EmailValidator.validateEmail(EMAIL, MIN_LENGTH));
    }
}
