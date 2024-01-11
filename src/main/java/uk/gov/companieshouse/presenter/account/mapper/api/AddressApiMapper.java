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
                .withAddressLine1(value.addressLine1())
                .withAddressLine2(value.addressLine2())
                .withCounty(value.county())
                .withCountry(value.country())
                .withPostcode(value.postcode())
                .withPremises(value.premises())
                .build();
    }
}
