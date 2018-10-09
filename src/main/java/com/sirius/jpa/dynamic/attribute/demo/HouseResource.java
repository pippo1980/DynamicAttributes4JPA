package com.sirius.jpa.dynamic.attribute.demo;

import com.sirius.jpa.dynamic.attribute.DynamicAttributes;
import com.sirius.jpa.dynamic.attribute.DynamicAttributesGroup;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Entity
public class HouseResource extends BaseEntity {

    protected Long publishAgent;

    protected Boolean audit = false;

    @Enumerated(EnumType.ORDINAL)
    protected HouseResourceStatus status;

    @Enumerated(EnumType.ORDINAL)
    protected HouseResourceStatus rent;

    @DynamicAttributesGroup(name = "city")
    @Transient
    protected DynamicAttributes cityAttributes;

    @DynamicAttributesGroup(name = "trade")
    @Transient
    protected DynamicAttributes tradeAttributes;

    public Long getPublishAgent() {
        return publishAgent;
    }

    public void setPublishAgent(Long publishAgent) {
        this.publishAgent = publishAgent;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }

    public HouseResourceStatus getStatus() {
        return status;
    }

    public void setStatus(HouseResourceStatus status) {
        this.status = status;
    }

    public HouseResourceStatus getRent() {
        return rent;
    }

    public void setRent(HouseResourceStatus rent) {
        this.rent = rent;
    }

    public DynamicAttributes getCityAttributes() {
        return cityAttributes;
    }

    public void setCityAttributes(DynamicAttributes cityAttributes) {
        this.cityAttributes = cityAttributes;
    }

    public DynamicAttributes getTradeAttributes() {
        return tradeAttributes;
    }

    public void setTradeAttributes(DynamicAttributes tradeAttributes) {
        this.tradeAttributes = tradeAttributes;
    }

    public enum HouseResourceStatus {
        validate,

        invalidate,
    }

    public enum HouseResourceType {

        new_house,

        second_hand,

        rent
    }

}
