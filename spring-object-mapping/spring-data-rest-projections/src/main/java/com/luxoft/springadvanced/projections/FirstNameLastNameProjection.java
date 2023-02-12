package com.luxoft.springadvanced.projections;

import org.springframework.data.rest.core.config.Projection;

@SuppressWarnings("unused")
@Projection(name = "firstName,lastName", types = Customer.class)
public interface FirstNameLastNameProjection {

	String getFirstname();
	String getLastname();
}
