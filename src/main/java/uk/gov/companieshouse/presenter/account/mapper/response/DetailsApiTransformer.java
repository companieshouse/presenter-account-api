package uk.gov.companieshouse.presenter.account.mapper.response;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountAddressApiBuilder;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountCompanyApiBuilder;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountDetailsApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountNameApiBuilder;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;

import java.util.Optional;

@Component
public class DetailsApiTransformer {

    public Optional<PresenterAccountDetailsApi> responseTransformer(Optional<PresenterAccountDetails> presenterAccountDetailsOptional){
        return presenterAccountDetailsOptional.map(presenterAccountDetails -> {
            PresenterAccountDetailsApi detailsApi = new PresenterAccountDetailsApi();

            detailsApi.setEmail(presenterAccountDetails.email());
            detailsApi.setLang(presenterAccountDetails.lang());
            detailsApi.setPresenterDetailsId(presenterAccountDetails.presenterDetailsId());

            String companyName = presenterAccountDetails.company().companyName();
            detailsApi.setCompany(PresenterAccountCompanyApiBuilder.createPresenterAccountCompanyApi(companyName).build());

            detailsApi.setName(PresenterAccountNameApiBuilder.createPresenterAccountNameApi()
                    .withForename(presenterAccountDetails.name().forename())
                    .withSurname(presenterAccountDetails.name().surname())
                    .build());

            detailsApi.setAddress(PresenterAccountAddressApiBuilder.createPresenterAccountAddressApi()
                    .withAddressLine1(presenterAccountDetails.address().addressLine1())
                    .withAddressLine2(presenterAccountDetails.address().addressLine2())
                    .withTownOrCity(presenterAccountDetails.address().townOrCity())
                    .withCountry(presenterAccountDetails.address().country())
                    .withPostcode(presenterAccountDetails.address().postcode())
                    .withPremises(presenterAccountDetails.address().premises())
                    .build());
            return detailsApi;
        });
    }
}
