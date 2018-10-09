package com.sirius.jpa.dynamic.attribute.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseResourceService extends BaseService<HouseResource, Long> {

    @Autowired
    public void setEntityRepository(JpaRepository<HouseResource, Long> repository) {
        this.entityRepository = repository;
    }

    @Transactional
    @Override
    public HouseResource save(HouseResource entity) {
        return super.save(entity);
    }
}
