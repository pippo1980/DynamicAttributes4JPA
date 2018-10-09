package com.sirius.jpa.dynamic.attribute;

import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "dynamic_attribute", uniqueConstraints = {@UniqueConstraint(columnNames = {"biz_id", "group_name", "attribute_key"})})
public class Attribute {

    public Attribute() {

    }

    public Attribute(String attributeKey, String attributeValue) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    Long id;

    @Column(name = "biz_id", nullable = false)
    String bizId;

    @Enumerated
    AttributeType attributeType;

    @Column(name = "group_name", nullable = false)
    String groupName;

    @Column(name = "attribute_key", nullable = false)
    String attributeKey;

    @Column(name = "attribute_value")
    String attributeValue;

    String displayName;
    String validator;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attribute attribute = (Attribute) o;
        return Objects.equals(bizId, attribute.bizId) &&
               Objects.equals(groupName, attribute.groupName) &&
               Objects.equals(attributeKey, attribute.attributeKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bizId, groupName, attributeKey);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Attribute{");
        sb.append("key='").append(attributeKey).append('\'');
        sb.append(", type='").append(attributeType).append('\'');
        sb.append(", value=").append(attributeValue);
        sb.append('}');
        return sb.toString();
    }
}
