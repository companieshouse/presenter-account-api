package uk.gov.companieshouse.presenter.account.mapper.api;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApiBuilder;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;

@Component
public class NameApiMapper implements Mapper<PresenterAccountNameApi, PresenterAccountName> {
    @Override
    public PresenterAccountNameApi map(PresenterAccountName value) {
        return PresenterAccountNameApiBuilder.createPresenterAccountNameApi()
                .withForename(value.forename())
                .withSurname(value.surname())
                .build();
    }
}
