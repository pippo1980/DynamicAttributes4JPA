package com.sirius.jpa.dynamic.attribute;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class EntityDynamicAttributePersister {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDynamicAttributePersister.class);

    @SuppressWarnings({"JpaQlInspection"})
    private static final String UPDATE = "update com.sirius.jpa.dynamic.attribute.Attribute " +
                                         "set attributeValue=:val " +
                                         "where bizId=:bizId and groupName=:groupName and attributeKey=:attributeKey";

    DynamicAttributePersister[] persisters;

    public EntityDynamicAttributePersister(Class<?> clazz) {

        Set<DynamicAttributePersister> persisters = new HashSet<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            DynamicAttributesGroup group = field.getAnnotation(DynamicAttributesGroup.class);
            if (group == null || field.getType() != DynamicAttributes.class) {
                continue;
            }

            persisters.add(new DynamicAttributePersister(group.name(), field));
        }

        this.persisters = persisters.toArray(new DynamicAttributePersister[0]);
    }

    public void persist(Session session, Serializable id, Object entity) {
        for (DynamicAttributePersister persister : persisters) {
            persister.persist(session, id, entity);
        }
    }

    class DynamicAttributePersister {

        public DynamicAttributePersister(String group, Field field) {
            this.group = group;
            this.field = field;
        }

        String group;
        Field field;

        void persist(Session session, Serializable id, Object entity) {
            try {
                Object fieldValue = field.get(entity);
                if (fieldValue == null) {
                    return;
                }

                DynamicAttributes attributes = (DynamicAttributes) fieldValue;
                if (entity == null || attributes.getAttributes() == null || attributes.getAttributes().size() == 0) {
                    return;
                }

                for (Attribute attribute : attributes.getAttributes()) {
                    int records = session.createQuery(UPDATE)
                            .setParameter("val", attribute.attributeValue)
                            .setParameter("bizId", id.toString())
                            .setParameter("groupName", group)
                            .setParameter("attributeKey", attribute.attributeKey)
                            .executeUpdate();

                    if (records == 0) {
                        attribute.setBizId(id.toString());
                        attribute.setGroupName(group);
                        session.save(attribute);
                    }
                }

                // TODO 字节码增强提高性能
                field.set(entity, attributes);
            } catch (Exception e) {
                LOGGER.error("load dynamic attributes[{}/{}] due to error:[{}]", group, field.getName(), e);
            }
        }

    }

}
