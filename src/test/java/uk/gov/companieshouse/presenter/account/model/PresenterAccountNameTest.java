package uk.gov.companieshouse.presenter.account.model;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

class PresenterAccountNameTest {

    private final static String FIRST_NAME = "test";
    private final static String LAST_NAME = "last";
    private final static String TOO_LONG_NAME = "a".repeat(41);
    private final static String INVALID_CHARACTOR_NAME = "Badname~";

    PresenterAccountName name;

    @Test
    @DisplayName("Create a valid presenter name")
    void testValidPresenterName() {
        name = new PresenterAccountName(FIRST_NAME, LAST_NAME);
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid present name with empty names")
    void testInvalidPresenterNameWithoutFirstOrSurname() {
        Exception firstNameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName("", LAST_NAME);
        });
        Exception surnameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(FIRST_NAME, "");
        });
        assertTrue(firstNameException.getMessage().contains("Name"));
        assertTrue(surnameException.getMessage().contains("Name"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid name with too long names")
    void testInvalidPresenterNameWithTooLongFirstOrSurname() {
        Exception firstNameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(TOO_LONG_NAME, LAST_NAME);
        });
        Exception surnameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(FIRST_NAME, TOO_LONG_NAME);
        });
        assertTrue(firstNameException.getMessage().contains("Name"));
        assertTrue(surnameException.getMessage().contains("Name"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid name with invalid characters names")
    void testInvalidPresenterNameWithInvalidCharacterFirstOrSurname() {
        Exception firstNameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(INVALID_CHARACTOR_NAME, LAST_NAME);
        });
        Exception surnameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(FIRST_NAME, INVALID_CHARACTOR_NAME);
        });
        assertTrue(firstNameException.getMessage().contains("Name"));
        assertTrue(surnameException.getMessage().contains("Name"));
    }
}
