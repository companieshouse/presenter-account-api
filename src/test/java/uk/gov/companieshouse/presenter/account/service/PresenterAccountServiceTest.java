package uk.gov.companieshouse.presenter.account.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;

@ExtendWith(MockitoExtension.class)
public class PresenterAccountServiceTest {
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final PresenterName NAME = new PresenterName("FIRST", "SECOND");
    private static final PresenterAddress ADDRESS = new PresenterAddress("a", "b", "c", "d", "e", "f");

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
        PresenterRequest presenterRequest = new PresenterRequest(USER_ID, EMAIL, NAME, ADDRESS);
        when(detailsMapper.map(presenterRequest)).thenReturn(presenterDetails);
        String presenterID = presenterAccountService.createPresenterAccount(presenterRequest);
        Assertions.assertNotNull(presenterID);
        verify(presenterAccountRepository, times(1)).save(presenterDetails);
    }
}