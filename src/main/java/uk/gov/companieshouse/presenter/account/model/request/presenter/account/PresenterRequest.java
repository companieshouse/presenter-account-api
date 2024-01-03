package uk.gov.companieshouse.presenter.account.model.request.presenter.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresenterRequest(
        @JsonProperty("chsUserId") String userId,
        String email,
        PresenterName name,
        PresenterAddress address) {
            @Override
            public String toString() {
                return "PresenterRequest";
            }
}
