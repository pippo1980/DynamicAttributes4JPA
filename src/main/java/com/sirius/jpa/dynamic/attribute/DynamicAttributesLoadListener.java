package com.sirius.jpa.dynamic.attribute;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;

import java.util.HashMap;
import java.util.Map;

public class DynamicAttributesLoadListener implements LoadEventListener, PostLoadEventListener {

    private final Map<String, EntityDynamicAttributeLoader> loaders = new HashMap<>();

    @Override
    public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
        if (loadType != LOAD && loadType != RELOAD && loadType != GET) {
            return;
        }

        EntityDynamicAttributeLoader loader = getEntityDynamicAttributeLoader(event.getEntityClassName());

        if (loader != null) {
            loader.load(event.getSession(), event.getEntityId(), event.getResult());
        }
    }

    @Override
    public void onPostLoad(PostLoadEvent event) {
        EntityDynamicAttributeLoader loader = getEntityDynamicAttributeLoader(event.getEntity().getClass().getName());

        if (loader != null) {
            loader.load(event.getSession(), event.getId(), event.getEntity());
        }
    }

    private EntityDynamicAttributeLoader getEntityDynamicAttributeLoader(String className) {
        EntityDynamicAttributeLoader loader = loaders.get(className);

        if (loader == null) {
            synchronized (loaders) {
                try {
                    loader = new EntityDynamicAttributeLoader(Thread.currentThread().getContextClassLoader().loadClass(
                            className));
                    loaders.put(className, loader);
                } catch (ClassNotFoundException e) {
                    throw new HibernateException(e);
                }
            }
        }

        return loader;
    }

}
