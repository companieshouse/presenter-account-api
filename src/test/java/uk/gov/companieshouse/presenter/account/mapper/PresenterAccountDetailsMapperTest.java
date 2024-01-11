package uk.gov.companieshouse.presenter.account.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountNameMapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;

@ExtendWith(MockitoExtension.class)
class PresenterAccountDetailsMapperTest {

    PresenterAccountDetailsMapper mapper;

    @Mock
    PresenterAccountAddressMapper addressMapper;

    @Mock
    PresenterAccountNameMapper nameMapper;

    private final static String PRESENTER_ID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private final static String USER_ID = "a";
    private final static String EMAIL = "a@a.a";

    @BeforeEach
    void before() {
        mapper = new PresenterAccountDetailsMapper(addressMapper, nameMapper);
    }

    @Test
    @DisplayName("Mapping from presenter request to presenter account details")
    void mapperTest() {
        PresenterNameRequest name = mock(PresenterNameRequest.class);
        PresenterAddressRequest address = mock(PresenterAddressRequest.class);
        PresenterAccountDetailsRequest request = new PresenterAccountDetailsRequest(USER_ID, EMAIL, name, address);

        when(nameMapper.map(name)).thenReturn(mock(PresenterAccountName.class));
        when(addressMapper.map(address)).thenReturn(mock(PresenterAccountAddress.class));

        PresenterAccountDetails details = mapper.map(PRESENTER_ID, request);

        assertEquals(PRESENTER_ID, details.presenterDetailsId());
        assertEquals(USER_ID, details.userId());
        assertEquals(EMAIL, details.email());
        assertEquals(address.addressLine1(), details.address().addressLine1());
        assertEquals(name.forename(), details.name().forename());

    }

    @Test
    @DisplayName("Mapping null to presenter account details")
    void mapNullDetailsTest() {
        assertThrows(ValidationException.class, () -> mapper.map(PRESENTER_ID, null));
    }

}
