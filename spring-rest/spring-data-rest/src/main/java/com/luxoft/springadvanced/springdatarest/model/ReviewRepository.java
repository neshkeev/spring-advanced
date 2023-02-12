package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RepositoryRestResource(path = "reviews", excerptProjection = ReviewRepository.ReviewProjection.class)
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @SuppressWarnings("unused")
    interface ReviewProjection {
        @Value("#{target.app.name}")
        String getApp();
        String getText();
    }
}
