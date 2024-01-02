package uk.gov.companieshouse.presenter.account.model.request.presenter.account;


public record PresenterName(String forename, String surname) {
    @Override
    public String toString(){
        return "PresenterAccountName";
    }
}
