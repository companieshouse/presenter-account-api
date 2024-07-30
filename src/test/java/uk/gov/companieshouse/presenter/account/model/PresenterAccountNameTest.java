package uk.gov.companieshouse.presenter.account.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        assertEquals(FIRST_NAME, name.forename());
        assertEquals(LAST_NAME, name.surname());
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

    @Test
    @DisplayName("Allows for null forename and surname in PresenterAccountName")
    void testAllowsForNullForenameAndSurname() {
        try {
            new PresenterAccountName(null, null);
        } catch (Exception e) {
            fail("Creation of PresenterAccountName should not fail with null forename and surname");
        }
    }

    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String firstString = "safe123";
        String lastString = "last";

        PresenterAccountName presenterAccountName = new PresenterAccountName(firstString, lastString);

        String presenterAccountNameString = presenterAccountName.toString();
        assertTrue(presenterAccountNameString.toString().contains("PresenterName"));
        assertFalse(presenterAccountNameString.toString().contains(firstString));
        assertFalse(presenterAccountNameString.toString().contains(lastString));
    }
}
