package uk.gov.companieshouse.presenter.account.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateTimeGenerator {

    public LocalDateTime currentDateTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

}
