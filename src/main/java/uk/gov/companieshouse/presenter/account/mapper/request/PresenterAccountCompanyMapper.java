package uk.gov.companieshouse.presenter.account.mapper.request;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;
import uk.gov.companieshouse.presenter.account.model.request.PresenterCompanyRequest;


@Component
public class PresenterAccountCompanyMapper implements Mapper<PresenterAccountCompany, PresenterCompanyRequest> {

    @Override
    public PresenterAccountCompany map(PresenterCompanyRequest value) {
        return new PresenterAccountCompany(
            value.companyName(),
            value.companyNumber()
        );
    }
    
}
