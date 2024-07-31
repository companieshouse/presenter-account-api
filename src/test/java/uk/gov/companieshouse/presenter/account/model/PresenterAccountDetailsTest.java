package uk.gov.companieshouse.presenter.account.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

class PresenterAccountDetailsTest {

    private static final String PRESENTER_ID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final String LANG = "en";
    private static final PresenterAccountName NAME = new PresenterAccountName("FIRST", "SECOND");
    private static final PresenterAccountAddress ADDRESS = new PresenterAccountAddress("a", "b", "c", "d", "e", "f");

    PresenterAccountDetails presenterDetails;

    @Test
    @DisplayName("Valid PresenterAccountDetails")
    void testValidPresenterAccountDetails() {
        presenterDetails = new PresenterAccountDetails(PRESENTER_ID, USER_ID, LANG, EMAIL, NAME, ADDRESS);
    }

    @Test
    @DisplayName("Throw ValidationException. Invalid PresenterAccountDetails empty or null parameters")
    void testInvalidPresenterAccountDetailsEmptyParameters() {
        Exception idException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, "", LANG, EMAIL, NAME, ADDRESS);
        });
        Exception langException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, "", EMAIL, NAME, ADDRESS);
        });
        Exception emailException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, LANG, "", NAME, ADDRESS);
        });
        Exception nameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, LANG, EMAIL, null, ADDRESS);
        });
        Exception addressException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, LANG, EMAIL, NAME, null);
        });

        assertTrue(idException.getMessage().contains("user"));
        assertTrue(langException.getMessage().contains("lang"));
        assertTrue(emailException.getMessage().contains("email"));
        assertTrue(nameException.getMessage().contains("name"));
        assertTrue(addressException.getMessage().contains("address"));
    }

    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String presenterAccountAddressString = "PresenterAccountDetail";
        String first = "ABC123";
        String second = "321CBA";
        String premiseString = "1";
        String line1String = "foo road";
        String line2String = "bar";
        String townString = "CITY";
        String countryString = "123456";
        String postcodeString = "AB12CD";

        String presenterId = "9c60fa56-d5c0-4c34-8e53-000000000";
        String email = "test.fail09876542345@test.test";
        String userId = "123";
        String lang = "cy";
        PresenterAccountName NAME = new PresenterAccountName(first, second);
        PresenterAccountAddress ADDRESS = new PresenterAccountAddress(premiseString, line1String, line2String, townString, countryString, postcodeString);

        presenterDetails = new PresenterAccountDetails(presenterId, userId, lang, email, NAME, ADDRESS);

        assertTrue(presenterDetails.toString().contains(presenterAccountAddressString));
        assertFalse(presenterDetails.toString().contains(premiseString));
        assertFalse(presenterDetails.toString().contains(line2String));
        assertFalse(presenterDetails.toString().contains(line1String));
        assertFalse(presenterDetails.toString().contains(presenterId));
        assertFalse(presenterDetails.toString().contains(email));
        assertFalse(presenterDetails.toString().contains(userId));
        assertFalse(presenterDetails.toString().contains(lang));
        assertFalse(presenterDetails.toString().contains(townString));
        assertFalse(presenterDetails.toString().contains(countryString));
        assertFalse(presenterDetails.toString().contains(postcodeString));
    }

}
