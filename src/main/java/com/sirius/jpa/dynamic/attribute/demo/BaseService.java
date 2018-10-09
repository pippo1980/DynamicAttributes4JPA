package com.sirius.jpa.dynamic.attribute.demo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class BaseService<T, ID> {

    public BaseService() {
    }

    public BaseService(JpaRepository<T, ID> entityRepository) {
        this.entityRepository = entityRepository;
    }

    protected JpaRepository<T, ID> entityRepository;

    public List<T> findAll() {
        return entityRepository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return entityRepository.findAll(sort);
    }

    public List<T> findAllById(Iterable<ID> ids) {
        return entityRepository.findAllById(ids);
    }

    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return entityRepository.saveAll(entities);
    }

    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        return entityRepository.saveAndFlush(entity);
    }

    @Transactional
    public void deleteInBatch(Iterable<T> entities) {
        entityRepository.deleteInBatch(entities);
    }

    @Transactional
    public void deleteAllInBatch() {
        entityRepository.deleteAllInBatch();
    }

    public T getOne(ID id) {
        return entityRepository.getOne(id);
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return entityRepository.findAll(example);
    }

    public <S extends T> List<S> findAll(Example<S> example,
            Sort sort) {
        return entityRepository.findAll(example, sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return entityRepository.findAll(pageable);
    }

    @Transactional
    public <S extends T> S save(S entity) {
        return entityRepository.save(entity);
    }

    public Optional<T> findById(ID id) {
        return entityRepository.findById(id);
    }

    public boolean existsById(ID id) {
        return entityRepository.existsById(id);
    }

    public long count() {
        return entityRepository.count();
    }

    @Transactional
    public void deleteById(ID id) {
        entityRepository.deleteById(id);
    }

    @Transactional
    public void delete(T entity) {
        entityRepository.delete(entity);
    }

    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {
        entityRepository.deleteAll(entities);
    }

    @Transactional
    public void deleteAll() {
        entityRepository.deleteAll();
    }

    public <S extends T> Optional<S> findOne(Example<S> example) {
        return entityRepository.findOne(example);
    }

    public <S extends T> Page<S> findAll(Example<S> example,
            Pageable pageable) {
        return entityRepository.findAll(example, pageable);
    }

    public <S extends T> long count(Example<S> example) {
        return entityRepository.count(example);
    }

    public <S extends T> boolean exists(Example<S> example) {
        return entityRepository.exists(example);
    }
}
