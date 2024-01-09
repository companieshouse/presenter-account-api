package uk.gov.companieshouse.presenter.account.mapper.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApi;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;

class AddressApiMapperTest {

    private final AddressApiMapper mapper = new AddressApiMapper();

    @Test
    void shouldMapPresenterAccountAddressToPresenterAccountAddressApi() {
        // given
        PresenterAccountAddress address = new PresenterAccountAddress("premises",
                "addressLine1",
                "addressLine2",
                "County",
                "Country",
                "Postcode");

        // when
        PresenterAccountAddressApi addressApi = mapper.map(address);

        // then
        assertEquals(address.getAddressLine1(), addressApi.getAddressLine1());
        assertEquals(address.getAddressLine2(), addressApi.getAddressLine2());
        assertEquals(address.getCounty(), addressApi.getCounty());
        assertEquals(address.getCountry(), addressApi.getCountry());
        assertEquals(address.getPostcode(), addressApi.getPostcode());
        assertEquals(address.getPremises(), addressApi.getPremises());
    }
}
