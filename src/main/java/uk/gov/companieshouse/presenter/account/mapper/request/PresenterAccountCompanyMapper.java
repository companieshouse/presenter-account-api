package uk.gov.companieshouse.presenter.account.mapper.request;

import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;
import uk.gov.companieshouse.presenter.account.model.request.PresenterCompanyRequest;

public class PresenterAccountCompanyMapper implements Mapper<PresenterAccountCompany, PresenterCompanyRequest> {

    @Override
    public PresenterAccountCompany map(PresenterCompanyRequest value) {
        return new PresenterAccountCompany(
            value.companyName(),
            value.companyNumber()
        );
    }
    
}
