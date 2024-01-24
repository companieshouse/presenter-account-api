package uk.gov.companieshouse.presenter.account.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresenterAccountDetailsRequest(
        @JsonProperty("userId") String userId,
        String email,
        PresenterNameRequest name,
        PresenterAddressRequest address) {
    @Override
    public String toString() {
        return "PresenterRequest";
    }
}
