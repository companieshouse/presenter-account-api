package uk.gov.companieshouse.presenter.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;

public interface PresenterAccountRepository extends MongoRepository<PresenterAccountDetails, String> {

}