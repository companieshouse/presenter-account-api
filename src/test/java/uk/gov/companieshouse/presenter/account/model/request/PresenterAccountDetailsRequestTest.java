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
        String firstString = "One";
        String lastString = "ĞĠĢĤĦÌÍÎÏĨĪ";
        
        String premiseString = "Flat";
        String line1String = "Building";
        String line2String = "Line Address";
        String townString = "Village Town";
        String countryString = "CountryString";
        String postcodeString = "ZZ00AA";
        
        String userId = "AAABB134";
        String lang = "en";
        String email = "fail.fail1@test.test";

        String companyName = "12345 Company!";
        String companyNumber = "NI999999";

        PresenterCompanyRequest company = new PresenterCompanyRequest(companyName, companyNumber);
        PresenterNameRequest name = new PresenterNameRequest(firstString, lastString);
        PresenterAddressRequest country = new PresenterAddressRequest(premiseString, line1String, line2String, townString, countryString, postcodeString);

        PresenterAccountDetailsRequest presenterDaDetailsRequest = new PresenterAccountDetailsRequest(userId, lang, email, company, name, country);

        String presenterAccountDetailsString = presenterDaDetailsRequest.toString();
        assertTrue(presenterAccountDetailsString.toString().contains(presenterRequestToString));
        assertFalse(presenterAccountDetailsString.toString().contains(line1String));
        assertFalse(presenterAccountDetailsString.toString().contains(line2String));
        assertFalse(presenterAccountDetailsString.toString().contains(townString));
        assertFalse(presenterAccountDetailsString.toString().contains(countryString));
        assertFalse(presenterAccountDetailsString.toString().contains(postcodeString));
        assertFalse(presenterAccountDetailsString.toString().contains(firstString));
        assertFalse(presenterAccountDetailsString.toString().contains(lastString));
        assertFalse(presenterAccountDetailsString.toString().contains(companyName));
        assertFalse(presenterAccountDetailsString.toString().contains(companyNumber));
    }
}
