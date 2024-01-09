package uk.gov.companieshouse.presenter.account.mapper.api;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApiBuilder;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;

@Component
public class AddressApiMapper implements Mapper<PresenterAccountAddressApi, PresenterAccountAddress> {
    @Override
    public PresenterAccountAddressApi map(PresenterAccountAddress value) {
        return PresenterAccountAddressApiBuilder.createPresenterAccountAddressApi()
                .withAddressLine1(value.getAddressLine1())
                .withAddressLine2(value.getAddressLine2())
                .withCounty(value.getCounty())
                .withCountry(value.getCountry())
                .withPostcode(value.getPostcode())
                .withPremises(value.getPremises())
                .build();
    }
}
