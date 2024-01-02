package uk.gov.companieshouse.presenter.account.model.request.presenter.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresenterAddress(String premises, String addressLine1, String addressLine2,
String county, String country, @JsonProperty("postCode") String postcode
){
    @Override
    public String toString(){
        return "PresenterAccountAddress";
    }
}
