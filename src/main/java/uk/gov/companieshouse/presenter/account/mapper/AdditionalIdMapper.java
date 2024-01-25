package uk.gov.companieshouse.presenter.account.mapper;

public interface AdditionalIdMapper<E, M> {

    E map(String id, M value);

}
