package com.sirius.jpa.dynamic.attribute.demo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public class BaseEntity implements Persistable<Long> {

    @PrePersist
    public void onPrePersist() {
        this.createdBy = null;
        this.createdDate = System.currentTimeMillis();
        this.onPreUpdate();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastModifiedBy = null;
        this.lastModifiedDate = System.currentTimeMillis();
    }

    @PreRemove
    public void onPreRemove() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    protected Long id;

    @Nullable
    protected Long createdBy;

    @Nullable
    protected Long createdDate;

    @Nullable
    protected Long lastModifiedBy;

    @Nullable
    protected Long lastModifiedDate;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    @Nullable
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(@Nullable Long createdBy) {
        this.createdBy = createdBy;
    }

    @Nullable
    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(@Nullable Long createdDate) {
        this.createdDate = createdDate;
    }

    @Nullable
    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(@Nullable Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Nullable
    public Long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(@Nullable Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        AbstractPersistable<?> that = (AbstractPersistable<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
