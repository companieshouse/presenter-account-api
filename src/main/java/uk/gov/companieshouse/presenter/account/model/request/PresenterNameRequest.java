package uk.gov.companieshouse.presenter.account.model.request;


public record PresenterNameRequest(String forename, String surname) {
    @Override
    public String toString() {
        return "PresenterAccountName";
    }
}
