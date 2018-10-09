package com.sirius.jpa.dynamic.attribute;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class DynamicAttributesIntegrator implements Integrator {

    @Override
    public void integrate(Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {

        EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        DynamicAttributesLoadListener loadListener = new DynamicAttributesLoadListener();
        eventListenerRegistry
                .getEventListenerGroup(EventType.LOAD)
                .appendListener(loadListener);
        eventListenerRegistry.getEventListenerGroup(EventType.POST_LOAD).appendListener(loadListener);

//        eventListenerRegistry.getEventListenerGroup(EventType.REFRESH)
//                .appendListener(new RefreshEventListenerImp());

        DynamicAttributesPersistListener listener = new DynamicAttributesPersistListener();
        eventListenerRegistry.getEventListenerGroup(EventType.SAVE).appendListener(listener);
        eventListenerRegistry.getEventListenerGroup(EventType.UPDATE).appendListener(listener);
        eventListenerRegistry.getEventListenerGroup(EventType.SAVE_UPDATE).appendListener(listener);
        eventListenerRegistry.getEventListenerGroup(EventType.PERSIST).appendListener(listener);
        eventListenerRegistry.getEventListenerGroup(EventType.PERSIST_ONFLUSH).appendListener(listener);
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
