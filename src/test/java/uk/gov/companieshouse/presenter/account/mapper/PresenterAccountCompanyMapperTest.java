package uk.gov.companieshouse.presenter.account.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountCompanyMapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterCompanyRequest;

public class PresenterAccountCompanyMapperTest {

    PresenterAccountCompanyMapper mapper;

    private final static String NAME = "company1pm--";
    private final static String NUMBER = "12345678";

    @BeforeEach
    void before() {
        mapper = new PresenterAccountCompanyMapper();
    }

    @Test
    @DisplayName("Mapping from presenter company to presenter account company")
    void mapperTest() {
        PresenterCompanyRequest name = new PresenterCompanyRequest(NAME, NUMBER);
        var accountName = mapper.map(name);
        assertEquals(NAME, accountName.companyName());
        assertEquals(NUMBER, accountName.companyNumber());
    }

    @Test
    @DisplayName("Mapping from presenter company with null company number to presenter account company")
    void mapperTestWithNullCompanyNumber() {
        PresenterCompanyRequest name = new PresenterCompanyRequest(NAME, null);
        var accountName = mapper.map(name);
        assertEquals(NAME, accountName.companyName());
        assertEquals(null, accountName.companyNumber());
    }

    @Test
    @DisplayName("Mapping null to presenter account name return ValidationException")
    void mapNullTest() {
        assertThrows(ValidationException.class, () -> mapper.map(null));
    }

}
