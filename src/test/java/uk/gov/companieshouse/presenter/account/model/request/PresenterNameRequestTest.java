package uk.gov.companieshouse.presenter.account.model.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PresenterAccountRequestTest {

    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String presenterAccountNameToString = "PresenterAccountName";
        String firstString = "First";
        String lastString = "Second";

        PresenterNameRequest presenterAccountName = new PresenterNameRequest(firstString,
                lastString);

        String presenterAccountNameString = presenterAccountName.toString();
        assertTrue(presenterAccountNameString.contains(presenterAccountNameToString));
        assertFalse(presenterAccountNameString.contains(firstString));
        assertFalse(presenterAccountNameString.contains(lastString));
    }
}