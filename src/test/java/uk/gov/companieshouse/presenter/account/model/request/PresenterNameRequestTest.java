package uk.gov.companieshouse.presenter.account.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class PresenterAccountRequestTest {

    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String presenterAccountNameToString = "PresenterAccountName";
        String firstString = "safe123";
        String lastString = "last";

        PresenterNameRequest presenterAccountName = new PresenterNameRequest(firstString, lastString);

        String presenterAccountNameString = presenterAccountName.toString();
        assertTrue(presenterAccountNameString.toString().contains(presenterAccountNameToString));
        assertFalse(presenterAccountNameString.toString().contains(firstString));
        assertFalse(presenterAccountNameString.toString().contains(lastString));
    }
}