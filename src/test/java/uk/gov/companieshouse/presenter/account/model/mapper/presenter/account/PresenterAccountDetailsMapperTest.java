package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account;

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
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountNameMapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;

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
        PresenterName name = mock(PresenterName.class);
        PresenterAddress address = mock(PresenterAddress.class);
        PresenterRequest request = new PresenterRequest(userId, email, name, address);
        
        when(nameMapper.map(name)).thenReturn(mock(PresenterAccountName.class));
        when(addressMapper.map(address)).thenReturn(mock(PresenterAccountAddress.class));
        
        PresenterAccountDetails details = mapper.map(request);

    }

    @Test
    @DisplayName("Mapping null to presenter account details")
    void mapNullTest() {
        assertThrows(ValidationException.class, () -> mapper.map(null));
    }
}
