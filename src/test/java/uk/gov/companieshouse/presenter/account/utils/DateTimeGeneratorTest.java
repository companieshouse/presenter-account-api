package uk.gov.companieshouse.presenter.account.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateTimeGeneratorTest {
    
    DateTimeGenerator dateTimeGenerator;

    @BeforeEach
    void beforeEach() {
        dateTimeGenerator = new DateTimeGenerator();
    }

    @Test
    public void testCurrentDateTimeMetaData() {
        LocalDateTime localDateTime = dateTimeGenerator.currentDateTime();
        assertTrue(localDateTime.toEpochSecond(ZoneOffset.UTC) > 1704151720);
    }

}
