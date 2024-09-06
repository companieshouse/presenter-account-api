package uk.gov.companieshouse.presenter.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApiBuilder;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountCompanyApiBuilder;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountDetailsApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApiBuilder;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.api.AddressApiMapper;
import uk.gov.companieshouse.presenter.account.mapper.api.CompanyApiMapper;
import uk.gov.companieshouse.presenter.account.mapper.api.DetailsApiMapper;
import uk.gov.companieshouse.presenter.account.mapper.api.NameApiMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountAddressMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountNameMapper;
import uk.gov.companieshouse.presenter.account.mapper.response.DetailsApiTransformer;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterCompanyRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;
import uk.gov.companieshouse.presenter.account.utils.IdGenerator;

@ExtendWith(MockitoExtension.class)
class PresenterAccountServiceTest {
    private static final String PRESENTER_ID = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
    private static final String EMAIL = "test@test.test";
    private static final String USER_ID = "userid";
    private static final String LANG = "en";
    private static final PresenterCompanyRequest COMPANY = new PresenterCompanyRequest("Business name", null);
    private static final PresenterAccountCompany ACCOUNT_COMPANY = new PresenterAccountCompany("Business name", null);
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

    @Mock
    IdGenerator idGenerator;

    @Mock
    DetailsApiTransformer detailsApiTransformer;

    @Mock
    private AddressApiMapper addressApiMapper;

    @Mock
    private NameApiMapper nameApiMapper;

    @Mock
    private CompanyApiMapper companyApiMapper;

    private DetailsApiMapper detailsApiMapper;

    @BeforeEach
    void setUp() {
        presenterAccountService = new PresenterAccountService(logger, detailsMapper, presenterAccountRepository,
                idGenerator,detailsApiTransformer);
        detailsApiMapper = new DetailsApiMapper(addressApiMapper,nameApiMapper,companyApiMapper);
    }

    @Test
    @DisplayName("Create presenter account details in mongo db")
    void testCreatePresenterAccount() {
        PresenterAccountDetails presenterDetails = new PresenterAccountDetails(PRESENTER_ID, USER_ID, LANG, EMAIL,
        ACCOUNT_COMPANY, ACCOUNT_NAME, ACCOUNT_ADDRESS);
        PresenterAccountDetailsRequest presenterRequest = new PresenterAccountDetailsRequest(USER_ID, LANG, EMAIL, COMPANY, NAME,
                ADDRESS);
        when(detailsMapper.map(PRESENTER_ID, presenterRequest)).thenReturn(presenterDetails);
        when(idGenerator.createUUID()).thenReturn(PRESENTER_ID);
        String presenterID = presenterAccountService.createPresenterAccount(presenterRequest);
        assertNotNull(presenterID);
        verify(presenterAccountRepository, times(1)).save(presenterDetails);
        assertTrue(Pattern.matches("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$", presenterID));
    }

    @Test
    void testGetPresenterAccount() {
        String presenterDetailsId = PRESENTER_ID;
        PresenterAccountDetails accountDetails = new PresenterAccountDetails(presenterDetailsId,
                "userId",
                "en",
                "test@example.com",
                new PresenterAccountCompany("Company Name 123", null),
                new PresenterAccountName("forename", "surname"),
                new PresenterAccountAddress("premises",
                        "addressLine1",
                        "addressLine2",
                        "townOrCity",
                        "country",
                        "postcode"));

        var accountDetailsApi = detailsApiMapper.map(accountDetails);

        when(presenterAccountRepository.findById(presenterDetailsId)).thenReturn(Optional.of(accountDetails));
        when(detailsApiTransformer.responseTransformer(Optional.of(accountDetails))).thenReturn(Optional.of(accountDetailsApi));

        Optional<PresenterAccountDetailsApi> result = presenterAccountService.getPresenterAccount(presenterDetailsId);

        assertTrue(result.isPresent());
        assertEquals(accountDetailsApi, result.get());
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
