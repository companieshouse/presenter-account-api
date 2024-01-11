package uk.gov.companieshouse.presenter.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;

@ExtendWith(MockitoExtension.class)
public class PresenterAccountServiceTest {
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final PresenterNameRequest NAME = new PresenterNameRequest("FIRST", "SECOND");
    private static final PresenterAddressRequest ADDRESS = new PresenterAddressRequest("a", "b", "c", "d", "e", "f");

    PresenterAccountService presenterAccountService;

    @Mock
    Logger logger;

    @Mock
    PresenterAccountRepository presenterAccountRepository;

    @Mock
    PresenterAccountDetailsMapper detailsMapper;

    @BeforeEach
    void setUp() {
        presenterAccountService = new PresenterAccountService(logger, detailsMapper, presenterAccountRepository);
    }

    @Test
    @DisplayName("Create presenter account details in mongo db")
    void testCreatePresenterAccount() {
        PresenterAccountDetails presenterDetails = new PresenterAccountDetails();
        PresenterAccountDetailsRequest presenterRequest = new PresenterAccountDetailsRequest(USER_ID, EMAIL, NAME, ADDRESS);
        when(detailsMapper.map(presenterRequest)).thenReturn(presenterDetails);
        String presenterID = presenterAccountService.createPresenterAccount(presenterRequest);
        Assertions.assertNotNull(presenterID);
        verify(presenterAccountRepository, times(1)).save(presenterDetails);
    }

    @Test
    public void testGetPresenterAccount() {
        String presenterAccountId = "123";
        PresenterAccountDetails accountDetails = new PresenterAccountDetails("userId",
                "test@example.com",
                new PresenterAccountName("forename", "surname"),
                new PresenterAccountAddress("premises",
                        "addressLine1",
                        "addressLine2",
                        "county",
                        "country",
                        "postcode"));

        when(presenterAccountRepository.findById(presenterAccountId)).thenReturn(Optional.of(accountDetails));

        Optional<PresenterAccountDetails> result = presenterAccountService.getPresenterAccount(presenterAccountId);

        assertTrue(result.isPresent());
        assertEquals(accountDetails, result.get());
        verify(presenterAccountRepository, times(1)).findById(presenterAccountId);
    }

    @Test
    public void testGetPresenterAccountThrowsException() {
        String presenterAccountId = "123";
        when(presenterAccountRepository.findById(presenterAccountId)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(RuntimeException.class,
                () -> presenterAccountService.getPresenterAccount(presenterAccountId));

        verify(logger, times(1)).error(anyString(), any(Exception.class));
        assertEquals(RuntimeException.class, exception.getClass());
    }
}
