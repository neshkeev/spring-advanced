package com.luxoft.springadvanced.transactions.propagation;

import com.luxoft.springadvanced.transactions.dao.ReviewRepository;
import com.luxoft.springadvanced.transactions.domain.Review;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class TransactionPropagationController {
    private final ReviewRepository reviewRepository;
    private final TransactionPropagationController controller;

    public TransactionPropagationController(ReviewRepository reviewRepository, @Lazy TransactionPropagationController controller) {
        this.reviewRepository = reviewRepository;
        this.controller = controller;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        controller.never();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        controller.mandatory();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        controller.notSupported();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        controller.nested();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        reviewRepository.save(new Review("Hello, mandatory!"));
        controller.nested();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        reviewRepository.save(new Review("Hello, World!"));
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
    }
}