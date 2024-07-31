package uk.gov.companieshouse.presenter.account.model.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PresenterAddressRequestTest {
    
    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String presenterAccountAddressString = "PresenterAccountAddress";
        String premiseString = "safe123";
        String line1String = "line12";
        String line2String = "foo";
        String townString = "town";
        String countryString = "Country";
        String postcodeString = "AB12CD";

        PresenterAddressRequest presenterAddress = new PresenterAddressRequest(premiseString, line1String, line2String,
            townString, countryString, postcodeString);

        assertTrue(presenterAddress.toString().contains(presenterAccountAddressString));
        assertFalse(presenterAddress.toString().contains(line1String));
        assertFalse(presenterAddress.toString().contains(line2String));
        assertFalse(presenterAddress.toString().contains(townString));
        assertFalse(presenterAddress.toString().contains(countryString));
        assertFalse(presenterAddress.toString().contains(postcodeString));
    }
}
