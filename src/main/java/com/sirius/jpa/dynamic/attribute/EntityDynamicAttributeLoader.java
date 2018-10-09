package com.sirius.jpa.dynamic.attribute;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityDynamicAttributeLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDynamicAttributeLoader.class);

    @SuppressWarnings({"JpaQlInspection"})
    private static final String QUERY = "select a from com.sirius.jpa.dynamic.attribute.Attribute a where a.bizId=:bizId and a.groupName=:groupName";

    DynamicAttributeLoader[] loaders;

    public EntityDynamicAttributeLoader(Class<?> clazz) {

        Set<DynamicAttributeLoader> _loaders = new HashSet<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            DynamicAttributesGroup group = field.getAnnotation(DynamicAttributesGroup.class);
            if (group == null || field.getType() != DynamicAttributes.class) {
                continue;
            }

            _loaders.add(new DynamicAttributeLoader(group.name(), field));
        }

        this.loaders = _loaders.toArray(new DynamicAttributeLoader[0]);
    }

    public void load(Session session, Serializable id, Object entity) {
        for (DynamicAttributeLoader loader : loaders) {
            loader.load(session, id, entity);
        }
    }

    class DynamicAttributeLoader {

        public DynamicAttributeLoader(String group, Field field) {
            this.group = group;
            this.field = field;
        }

        String group;
        Field field;

        void load(Session session, Serializable id, Object entity) {
            try {
                Query query = session.createQuery(QUERY);
                @SuppressWarnings("unchecked")
                List<Attribute> result = query.setParameter("bizId", id.toString())
                        .setParameter("groupName", group)
                        .getResultList();

                if (result == null || result.isEmpty()) {
                    return;
                }

                DynamicAttributes attributes = new DynamicAttributes(new HashSet<>(result));

                // TODO 拿到的是hibernate的代理类, 反射设置field时, get方法并不是取到该field
                field.set(entity, attributes);

                // 使用当前对象的set方法来设置属性
                String methodName = "set" + StringUtils.capitalize(field.getName());
                Method setter = entity.getClass().getDeclaredMethod(methodName, DynamicAttributes.class);
                setter.invoke(entity, attributes);
            } catch (Exception e) {
                LOGGER.error("load dynamic attributes[{}/{}] due to error:[{}]", group, field.getName(), e);
            }
        }

    }

}
