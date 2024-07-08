package uk.gov.companieshouse.presenter.account.mapper.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApiBuilder;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApiBuilder;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;

class DetailsApiMapperTest {

    @Mock
    private AddressApiMapper addressMapper;

    @Mock
    private NameApiMapper nameMapper;

    private DetailsApiMapper detailsApiMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        detailsApiMapper = new DetailsApiMapper(addressMapper, nameMapper);
    }

    @Test
    void testMap() {
        // Arrange
        // Replace these parameters with the actual parameters for PresenterAccountDetails' constructor
        String presenterDetailsId = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
        String userId = "user123";
        String email = "example@example.com";
        String lang = "en";
        var name = new PresenterAccountName("John", "Doe");
        var address = new PresenterAccountAddress("premises",
                "addressLine1",
                "addressLine2",
                "County",
                "Country",
                "Postcode");

        var presenterAccountDetails = new PresenterAccountDetails(presenterDetailsId, userId, lang, email, name, address);

        when(nameMapper.map(name)).thenReturn(PresenterAccountNameApiBuilder.createPresenterAccountNameApi()
                .withForename("John")
                .withSurname("Doe").build());
        when(addressMapper.map(address)).thenReturn(PresenterAccountAddressApiBuilder
                .createPresenterAccountAddressApi()
                .withPremises("Premises")
                .withAddressLine1("addressLine1")
                .withAddressLine2("addressLine2")
                .withTownOrCity("Town Or City")
                .withCountry("Country")
                .withPostcode("Postcode")
                .build());

        // Act
        var result = detailsApiMapper.map(presenterAccountDetails);

        // Assert
        assertEquals(userId, result.getUserId());
        assertEquals(presenterDetailsId, result.getPresenterDetailsId());
        assertEquals(email, result.getEmail());
    }
}
