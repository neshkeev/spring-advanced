package com.luxoft.springadvanced.springrestevents.listener;

import com.luxoft.springadvanced.springdatarest.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;

@RepositoryEventHandler
public class ReviewEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewEventHandler.class);

    @HandleAfterCreate
    public void handleAfterCreate(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleAfterDelete
    public void handleAfterDelete(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleAfterSave
    public void handleAfter(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleAfterLinkDelete
    public void handleAfterLinkDelete(Review review, Object o) {
        LOG.info("Handler After " + review);
    }

    @HandleAfterLinkSave
    public void handleAfterLinkSave(Review review, Object o) {
        LOG.info("Handler After " + review);
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleBeforeDelete
    public void handleBeforeDelete(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleBeforeSave
    public void handleBeforeSave(Review review) {
        LOG.info("Handler After " + review);
    }

    @HandleBeforeLinkDelete
    public void handleBeforeLinkDelete(Review review, Object o) {
        LOG.info("Handler After " + review);
    }

    @HandleBeforeLinkSave
    public void handleBeforeLinkSave(Review review, Object o) {
        LOG.info("Handler After " + review);
    }
}
