package uk.gov.companieshouse.presenter.account.mapper;

import static org.junit.Assert.assertThrows;
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

    private final static String userId = "a";
    private final static String email = "a@a.a";

    @BeforeEach
    void before() {
        mapper = new PresenterAccountDetailsMapper(addressMapper, nameMapper);
    }

    @Test
    @DisplayName("Mapping from presenter request to presenter account details")
    void mapperTest() {
        PresenterNameRequest name = mock(PresenterNameRequest.class);
        PresenterAddressRequest address = mock(PresenterAddressRequest.class);
        PresenterAccountDetailsRequest request = new PresenterAccountDetailsRequest(userId, email, name, address);

        when(nameMapper.map(name)).thenReturn(mock(PresenterAccountName.class));
        when(addressMapper.map(address)).thenReturn(mock(PresenterAccountAddress.class));

        // TODO: complete this test
        @SuppressWarnings("unused") PresenterAccountDetails details = mapper.map(request);

    }

    @Test
    @DisplayName("Mapping null to presenter account details")
    void mapNullTest() {
        assertThrows(ValidationException.class, () -> mapper.map(null));
    }
}
