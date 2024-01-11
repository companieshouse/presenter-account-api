package uk.gov.companieshouse.presenter.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountNameMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;

@ExtendWith(MockitoExtension.class)
class PresenterAccountServiceTest {
    private static final String PRESENTER_ID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final PresenterNameRequest NAME = new PresenterNameRequest("FIRST", "SECOND");
    private static final PresenterAccountName ACCOUNT_NAME = new PresenterAccountName("FIRST", "SECOND");
    private static final PresenterAddressRequest ADDRESS = new PresenterAddressRequest("a", "b", "c", "d", "e", "f");
    private static final PresenterAccountAddress ACCOUNT_ADDRESS = new PresenterAccountAddress("a", "b", "c", "d", "e",
            "f");

    PresenterAccountService presenterAccountService;

    @Mock
    Logger logger;

    @Mock
    PresenterAccountRepository presenterAccountRepository;

    @Mock
    PresenterAccountDetailsMapper detailsMapper;

    @Mock
    PresenterAccountNameMapper nameMapper;

    @Mock
    PresenterAccountAddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        presenterAccountService = new PresenterAccountService(logger, detailsMapper, presenterAccountRepository);
    }

    @Test
    @DisplayName("Create presenter account details in mongo db")
    void testCreatePresenterAccount() {
        PresenterAccountDetails presenterDetails = new PresenterAccountDetails(PRESENTER_ID, USER_ID, EMAIL,
                ACCOUNT_NAME, ACCOUNT_ADDRESS);
        PresenterAccountDetailsRequest presenterRequest = new PresenterAccountDetailsRequest(USER_ID, EMAIL, NAME,
                ADDRESS);
        when(detailsMapper.map(PRESENTER_ID, presenterRequest)).thenReturn(presenterDetails);
        String presenterID = presenterAccountService.createPresenterAccount(presenterRequest, PRESENTER_ID);
        assertNotNull(presenterID);
        verify(presenterAccountRepository, times(1)).save(presenterDetails);
    }

    @Test
    @DisplayName("Create presenter account details without defining uuid")
    void testCreatePresenterAccountUUIDcreation() {
        var detailsMapper = new PresenterAccountDetailsMapper(addressMapper, nameMapper);
        var presenterAccountService = new PresenterAccountService(logger, detailsMapper, presenterAccountRepository);
        PresenterAccountDetailsRequest presenterRequest = new PresenterAccountDetailsRequest(USER_ID, EMAIL, NAME,
                ADDRESS);

        when(nameMapper.map(NAME)).thenReturn(ACCOUNT_NAME);
        when(addressMapper.map(ADDRESS)).thenReturn(ACCOUNT_ADDRESS);

        var presenterID = presenterAccountService.createPresenterAccount(presenterRequest);

        assertTrue(Pattern.matches("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$", presenterID));
    }

    @Test
    void testGetPresenterAccount() {
        String presenterDetailsId = PRESENTER_ID;
        PresenterAccountDetails accountDetails = new PresenterAccountDetails(presenterDetailsId,
                "userId",
                "test@example.com",
                new PresenterAccountName("forename", "surname"),
                new PresenterAccountAddress("premises",
                        "addressLine1",
                        "addressLine2",
                        "county",
                        "country",
                        "postcode"));

        when(presenterAccountRepository.findById(presenterDetailsId)).thenReturn(Optional.of(accountDetails));

        Optional<PresenterAccountDetails> result = presenterAccountService.getPresenterAccount(presenterDetailsId);

        assertTrue(result.isPresent());
        assertEquals(accountDetails, result.get());
        verify(presenterAccountRepository, times(1)).findById(presenterDetailsId);
    }

    @Test
    void testGetPresenterAccountThrowsException() {
        String presenterDetailsId = "123";
        when(presenterAccountRepository.findById(presenterDetailsId)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(RuntimeException.class,
                () -> presenterAccountService.getPresenterAccount(presenterDetailsId));

        verify(logger, times(1)).error(anyString(), any(Exception.class));
        assertEquals(RuntimeException.class, exception.getClass());
    }
}
