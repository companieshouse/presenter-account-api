package uk.gov.companieshouse.presenter.account.mapper.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountCompanyApi;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;

public class CompanyApiMapperTest {
    @InjectMocks
    private CompanyApiMapper nameApiMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void map() {
        // Arrange
        PresenterAccountCompany presenterAccountCompany = new PresenterAccountCompany("John", "Doe");

        // Act
        PresenterAccountCompanyApi presenterAccountCompanyApi = nameApiMapper.map(presenterAccountCompany);

        // Assert
        assertEquals(presenterAccountCompany.companyName(), presenterAccountCompanyApi.getCompanyName());
        assertEquals(presenterAccountCompany.companyNumber(), presenterAccountCompanyApi.getCompanyNumber());
    }
}
