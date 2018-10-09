package com.sirius.jpa.dynamic.attribute;

import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

public class DynamicAttributes {

    public DynamicAttributes() {

    }

    public DynamicAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getValue(String key) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }

        return attributes.parallelStream()
                .filter(attribute -> attribute.attributeKey.equals(key))
                .findFirst()
                .get().attributeValue;
    }

    public void putValue(String key, String value) {
        this.attributes.add(new Attribute(key, value));
    }

    @Transient
    private Set<Attribute> attributes = new HashSet<>();

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
