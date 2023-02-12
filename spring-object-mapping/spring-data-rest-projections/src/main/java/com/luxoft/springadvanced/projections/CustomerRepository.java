package com.luxoft.springadvanced.projections;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = FirstNameLastNameAddressProjection.class)
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
