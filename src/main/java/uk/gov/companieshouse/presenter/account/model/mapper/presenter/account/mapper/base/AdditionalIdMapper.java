package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base;

public interface AdditionalIdMapper<E, M> {

    E map(String id, M value);

}
