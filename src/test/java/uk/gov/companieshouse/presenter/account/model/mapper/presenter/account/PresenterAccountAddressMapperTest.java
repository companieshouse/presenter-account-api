package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;

class PresenterAccountAddressMapperTest {
    
    PresenterAccountAddressMapper mapper;

    private final static String premises = "a";
    private final static String line1 = "b";
    private final static String country = "e";
    private final static String postcode = "f";

    @BeforeEach
    void before(){
        mapper = new PresenterAccountAddressMapper();
    }


    @Test
    @DisplayName("Mapping from presenter address to presenter account address")
    void mapperTest() {
        PresenterAddress address = new PresenterAddress(premises, line1, null, null, country, postcode);
        PresenterAccountAddress accountAddress = mapper.map(address);
        assertEquals(premises, accountAddress.getPremises());
        assertEquals(line1, accountAddress.getAddressLine1());
        assertEquals("", accountAddress.getAddressLine2());
        assertEquals("", accountAddress.getCounty());
        assertEquals(country, accountAddress.getCountry());
        assertEquals(postcode, accountAddress.getPostcode());
    }

}
