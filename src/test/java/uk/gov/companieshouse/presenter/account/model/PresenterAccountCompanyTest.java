package uk.gov.companieshouse.presenter.account.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

public class PresenterAccountCompanyTest {
    
    private final static String NAME = "test";
    private final static String NUMBER = "01234567";
    private final static String NUMBER_MISSING = null;
    private final static String NAME_TOO_LONG = "a".repeat(81);
    private final static String INVALID_NAME = "Company~";
    private final static String NUMBER_TOO_LONG = "NI12345678";

    PresenterAccountCompany company;

    @Test
    @DisplayName("Create a valid presenter company")
    void testValidPresenterCompany() {
        company = new PresenterAccountCompany(NAME, NUMBER);
        assertEquals(NAME, company.companyName());
        assertEquals(NUMBER, company.companyNumber());
    }

    @Test
    @DisplayName("Create a valid presenter company - no company number")
    void testValidPresenterCompanyWithNoCompanyNumber() {
        company = new PresenterAccountCompany(NAME, NUMBER_MISSING);
        assertEquals(NAME, company.companyName());
        assertEquals(NUMBER_MISSING, company.companyNumber());
    }

    @Test
    @DisplayName("Create a invalid presenter company - too long name")
    void testInvalidPresenterCompanyWithTooLongName() {
        Exception nameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountCompany(NAME_TOO_LONG, NUMBER);
        });
        assertTrue(nameException.getMessage().contains("company"));
    }

    @Test
    @DisplayName("Create a invalid presenter company - too long number")
    void testInvalidPresenterCompanyWithTooLongNumber() {
        Exception numberException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountCompany(NAME, NUMBER_TOO_LONG);
        });
        assertTrue(numberException.getMessage().contains("company"));
    }

    @Test
    @DisplayName("Create a invalid presenter company - invalid name")
    void testInvalidPresenterCompanyWithInvalidName() {
        Exception nameException = assertThrows(ValidationException.class, () -> {
            new PresenterAccountCompany(INVALID_NAME, NUMBER);
        });
        assertTrue(nameException.getMessage().contains("company"));
    }
}
