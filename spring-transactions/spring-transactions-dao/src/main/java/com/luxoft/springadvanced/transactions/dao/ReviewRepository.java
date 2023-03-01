package com.luxoft.springadvanced.transactions.dao;

import com.luxoft.springadvanced.transactions.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
