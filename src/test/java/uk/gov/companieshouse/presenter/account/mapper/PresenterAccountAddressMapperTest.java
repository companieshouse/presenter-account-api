package uk.gov.companieshouse.presenter.account.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;

class PresenterAccountAddressMapperTest {

    PresenterAccountAddressMapper mapper;

    private final static String premises = "a";
    private final static String line1 = "b";
    private final static String country = "e";
    private final static String postcode = "f";

    @BeforeEach
    void before() {
        mapper = new PresenterAccountAddressMapper();
    }

    @Test
    @DisplayName("Mapping from presenter address to presenter account address")
    void mapperTest() {
        PresenterAddressRequest address = new PresenterAddressRequest(premises, line1, null, null, country, postcode);
        PresenterAccountAddress accountAddress = mapper.map(address);
        assertEquals(premises, accountAddress.premises());
        assertEquals(line1, accountAddress.addressLine1());
        assertEquals("", accountAddress.addressLine2());
        assertEquals("", accountAddress.townOrCity());
        assertEquals(country, accountAddress.country());
        assertEquals(postcode, accountAddress.postcode());
    }

    @Test
    @DisplayName("Mapping null to presenter account address return ValidationException")
    void mapNullTest() {
        assertThrows(ValidationException.class, () -> mapper.map(null));
    }

}
