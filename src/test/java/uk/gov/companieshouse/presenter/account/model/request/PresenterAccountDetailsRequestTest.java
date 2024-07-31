package uk.gov.companieshouse.presenter.account.model.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PresenterAccountDetailsRequestTest {
    
    @Test
    @DisplayName("Test that toString is safe")
    void testToString() {
        String presenterRequestToString = "PresenterRequest";
        String firstString = ",.adfbeif";
        String lastString = "ĞĠĢĤĦÌÍÎÏĨĪ";
        
        String premiseString = "carrot";
        String line1String = "cabbage";
        String line2String = "cucumber";
        String townString = "BIRD";
        String countryString = "MARS";
        String postcodeString = "ĞĠĢĤĦÌÍ";
        
        String userId = "AAABB134";
        String lang = "en";
        String email = "fail.fail1@test.test";
        PresenterNameRequest name = new PresenterNameRequest(firstString, lastString);
        PresenterAddressRequest country = new PresenterAddressRequest(premiseString, line1String, line2String, townString, countryString, postcodeString);

        PresenterAccountDetailsRequest presenterDaDetailsRequest = new PresenterAccountDetailsRequest(userId, lang, email, name, country);

        String presenterAccountDetailsString = presenterDaDetailsRequest.toString();
        assertTrue(presenterAccountDetailsString.toString().contains(presenterRequestToString));
        assertFalse(presenterAccountDetailsString.toString().contains(line1String));
        assertFalse(presenterAccountDetailsString.toString().contains(line2String));
        assertFalse(presenterAccountDetailsString.toString().contains(townString));
        assertFalse(presenterAccountDetailsString.toString().contains(countryString));
        assertFalse(presenterAccountDetailsString.toString().contains(postcodeString));
        assertFalse(presenterAccountDetailsString.toString().contains(firstString));
        assertFalse(presenterAccountDetailsString.toString().contains(lastString));
    }
}
