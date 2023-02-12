package com.luxoft.springadvanced.springrestevents.listener;

import com.luxoft.springadvanced.springdatarest.model.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;

@Service
public class AppEventListener extends AbstractRepositoryEventListener<App> {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewEventHandler.class);

    @Override
    protected void onBeforeCreate(App entity) {
        if ("FB".equals(entity.getName())) {
            LOG.warn("FB is prohibited from conducting its business on Russia's territory");
        }
        LOG.info("onBeforeCreate: " + entity);
    }

    @Override
    protected void onAfterCreate(App entity) {
        LOG.info("onAfterCreate: " + entity);
    }

    @Override
    protected void onBeforeSave(App entity) {
        LOG.info("onBeforeSave: " + entity);
    }

    @Override
    protected void onAfterSave(App entity) {
        LOG.info("onAfterSave: " + entity);
    }

    @Override
    protected void onBeforeLinkSave(App parent, Object linked) {
        LOG.info("onBeforeLinkSave: parent: {} linked: {}", parent, linked);
    }

    @Override
    protected void onAfterLinkSave(App parent, Object linked) {
        LOG.info("onAfterLinkSave: parent: {} linked: {}", parent, linked);
    }

    @Override
    protected void onBeforeLinkDelete(App parent, Object linked) {
        LOG.info("onBeforeLinkDelete: parent: {} linked: {}", parent, linked);
    }

    @Override
    protected void onAfterLinkDelete(App parent, Object linked) {
        LOG.info("onAfterLinkDelete: parent: {} linked: {} ", parent, linked);
    }

    @Override
    protected void onBeforeDelete(App entity) {
        LOG.info("onBeforeDelete: " + entity);
    }

    @Override
    protected void onAfterDelete(App entity) {
        LOG.info("onAfterDelete: " + entity);
    }
}
