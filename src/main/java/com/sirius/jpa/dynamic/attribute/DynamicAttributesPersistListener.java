package com.sirius.jpa.dynamic.attribute;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DynamicAttributesPersistListener implements SaveOrUpdateEventListener, PersistEventListener {

    private final Map<Class<?>, EntityDynamicAttributePersister> persisters = new HashMap<>();

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        if (event.getResultId() == null) {
            return;
        }

        EntityDynamicAttributePersister persister = getPersister(event.getEntity().getClass());
        if (persister == null) {
            return;
        }

        persister.persist(event.getSession(), event.getResultId(), event.getEntity());
    }

    @Override
    public void onPersist(PersistEvent event) throws HibernateException {
        Object entity = event.getObject();

        if (!(entity instanceof Persistable)) {
            // TODO warn logger
            return;
        }

        Persistable persistable = (Persistable) entity;

        EntityDynamicAttributePersister persister = getPersister(persistable.getClass());
        if (persister == null) {
            return;
        }

        persister.persist(event.getSession(), (Serializable) persistable.getId(), persistable);
    }

    @Override
    public void onPersist(PersistEvent event, Map createdAlready) throws HibernateException {
        onPersist(event);
    }

    private EntityDynamicAttributePersister getPersister(Class<?> clazz) {
        EntityDynamicAttributePersister persister = persisters.get(clazz);

        if (persister == null) {
            synchronized (persisters) {
                try {
                    persister = new EntityDynamicAttributePersister(clazz);
                    persisters.put(clazz, persister);
                } catch (Exception e) {
                    throw new HibernateException(e);
                }
            }
        }

        return persister;
    }
}
