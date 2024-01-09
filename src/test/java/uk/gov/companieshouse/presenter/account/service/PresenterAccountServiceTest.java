package uk.gov.companieshouse.presenter.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountDetailsRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PresenterAccountServiceTest {

    @Mock
    private Logger logger;

    @Mock
    private PresenterAccountDetailsRepository repository;

    @InjectMocks
    private PresenterAccountService service;

    @BeforeEach
    public void setUp() {
        service = new PresenterAccountService(logger, repository, null);
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
        when(repository.findById(presenterAccountId)).thenReturn(Optional.of(accountDetails));

        Optional<PresenterAccountDetails> result = service.getPresenterAccount(presenterAccountId);

        assertTrue(result.isPresent());
        assertEquals(accountDetails, result.get());
        verify(repository, times(1)).findById(presenterAccountId);
    }

    @Test
    public void testGetPresenterAccountThrowsException() {
        String presenterAccountId = "123";
        when(repository.findById(presenterAccountId)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(RuntimeException.class, () -> service.getPresenterAccount(presenterAccountId));

        verify(logger, times(1)).error(anyString(), any(Exception.class));
        assertEquals(RuntimeException.class, exception.getClass());
    }
}
