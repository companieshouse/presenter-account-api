package uk.gov.companieshouse.presenter.account.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

class PresenterAccountDetailsTest {

    private static final String PRESENTER_ID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final PresenterAccountName NAME = new PresenterAccountName("FIRST", "SECOND");
    private static final PresenterAccountAddress ADDRESS = new PresenterAccountAddress("a", "b", "c", "d", "e", "f");

    PresenterAccountDetails presenterDetails;

    @Test
    @DisplayName("Valid PresenterAccountDetails")
    void testValidPresenterAccountDetails() {
        presenterDetails = new PresenterAccountDetails(PRESENTER_ID, USER_ID, EMAIL, NAME, ADDRESS);
    }

    @Test
    @DisplayName("Throw ValidationException. Invalid PresenterAccountDetails empty or null parameters")
    void testInvalidPresenterAccountDetailsEmptyParameters() {
        Exception idException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, "", EMAIL, NAME, ADDRESS);
        });
        Exception emailException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, "", NAME, ADDRESS);
        });
        Exception nameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, EMAIL, null, ADDRESS);
        });
        Exception addressException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountDetails(PRESENTER_ID, USER_ID, EMAIL, NAME, null);
        });

        assertTrue(idException.getMessage().contains("user"));
        assertTrue(emailException.getMessage().contains("email"));
        assertTrue(nameException.getMessage().contains("name"));
        assertTrue(addressException.getMessage().contains("address"));
    }

}
