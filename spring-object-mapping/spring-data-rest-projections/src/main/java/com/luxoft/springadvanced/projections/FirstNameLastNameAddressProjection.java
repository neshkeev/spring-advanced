package com.luxoft.springadvanced.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "firstName,lastName,address", types = Customer.class)
public interface FirstNameLastNameAddressProjection  extends FirstNameLastNameProjection {

	@Value("#{target.address==null ? 'no address' : target.address.toString()}")
	String getAddress();
}
