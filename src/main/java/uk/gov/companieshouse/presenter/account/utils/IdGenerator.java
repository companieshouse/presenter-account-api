package uk.gov.companieshouse.presenter.account.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    public String createUUID() {
        return UUID.randomUUID().toString();
    }

}
