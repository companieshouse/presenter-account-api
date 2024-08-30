package uk.gov.companieshouse.presenter.account.mapper.request;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;
import uk.gov.companieshouse.presenter.account.model.request.PresenterCompanyRequest;

@Component
public class PresenterAccountCompanyMapper implements Mapper<PresenterAccountCompany, PresenterCompanyRequest> {

    @Override
    public PresenterAccountCompany map(PresenterCompanyRequest value) {
        if (value != null) {
            return new PresenterAccountCompany(
                    value.companyName(),
                    value.companyNumber());
        } else {
            throw new ValidationException("Presenter company details are missing");
        }
    }

}
