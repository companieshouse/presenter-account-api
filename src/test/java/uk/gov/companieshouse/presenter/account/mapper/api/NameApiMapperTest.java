package uk.gov.companieshouse.presenter.account.mapper.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApi;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;

class NameApiMapperTest {

    @InjectMocks
    private NameApiMapper nameApiMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void map() {
        // Arrange
        PresenterAccountName presenterAccountName = new PresenterAccountName("John", "Doe");

        // Act
        PresenterAccountNameApi presenterAccountNameApi = nameApiMapper.map(presenterAccountName);

        // Assert
        assertEquals(presenterAccountName.getForename(), presenterAccountNameApi.getForename());
        assertEquals(presenterAccountName.getSurname(), presenterAccountNameApi.getSurname());
    }
}
