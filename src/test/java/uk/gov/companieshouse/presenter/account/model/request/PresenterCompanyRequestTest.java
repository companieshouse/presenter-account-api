package uk.gov.companieshouse.presenter.account.model.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

public class PresenterCompanyRequestTest {
    
    private static final String COMPANY_NUMBER = "01234567";
    private static final String COMPANY_NAME = "company...Name";

    @Test
    void testWhetherCompanyNumberCanBeNull() {
        PresenterCompanyRequest presenterCompanyRequest = new PresenterCompanyRequest(COMPANY_NAME, null);
        assertEquals(COMPANY_NAME, presenterCompanyRequest.companyName());
        assertNull(presenterCompanyRequest.companyNumber());
        assertEquals("PresenterCompanyRequest[companyName=%s, companyNumber=%s]".formatted(COMPANY_NAME, "null"), presenterCompanyRequest.toString());
    }

    @Test
    void testBothCompanyNameAndNumberSet() {
        PresenterCompanyRequest presenterCompanyRequest = new PresenterCompanyRequest(COMPANY_NAME, COMPANY_NUMBER);
        assertEquals(COMPANY_NAME, presenterCompanyRequest.companyName());
        assertEquals(COMPANY_NUMBER, presenterCompanyRequest.companyNumber());
        assertEquals("PresenterCompanyRequest[companyName=%s, companyNumber=%s]".formatted(COMPANY_NAME, COMPANY_NUMBER), presenterCompanyRequest.toString());
    }

}
