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
                "townOrCity",
                "Country",
                "Postcode");

        // when
        PresenterAccountAddressApi addressApi = mapper.map(address);

        // then
        assertEquals(address.addressLine1(), addressApi.getAddressLine1());
        assertEquals(address.addressLine2(), addressApi.getAddressLine2());
        assertEquals(address.townOrCity(), addressApi.getTownOrCity());
        assertEquals(address.country(), addressApi.getCountry());
        assertEquals(address.postcode(), addressApi.getPostcode());
        assertEquals(address.premises(), addressApi.getPremises());
    }
}
