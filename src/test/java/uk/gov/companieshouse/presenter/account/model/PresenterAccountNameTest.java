package uk.gov.companieshouse.presenter.account.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

class PresenterAccountNameTest {

    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "last";
    private static final String TOO_LONG_NAME = "a".repeat(41);
    private static final String INVALID_CHARACTER_NAME = "Badname~";

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
            new PresenterAccountName(INVALID_CHARACTER_NAME, LAST_NAME);
        });
        Exception surnameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountName(FIRST_NAME, INVALID_CHARACTER_NAME);
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
        String firstString = "Safe";
        String lastString = "Last";

        PresenterAccountName presenterAccountName = new PresenterAccountName(firstString,
                lastString);

        String presenterAccountNameString = presenterAccountName.toString();
        assertTrue(presenterAccountNameString.contains("PresenterName"));
        assertFalse(presenterAccountNameString.contains(firstString));
        assertFalse(presenterAccountNameString.contains(lastString));
    }
}
