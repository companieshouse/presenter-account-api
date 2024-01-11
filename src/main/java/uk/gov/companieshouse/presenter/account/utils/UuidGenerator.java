package uk.gov.companieshouse.presenter.account.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidGenerator {

    public UuidGenerator() {
    }
    
    public UUID createUUID() {
        return UUID.randomUUID();
    } 

}
