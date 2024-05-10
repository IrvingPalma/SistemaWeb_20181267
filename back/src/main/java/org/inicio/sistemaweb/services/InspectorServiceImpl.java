package org.inicio.sistemaweb.services;

import org.inicio.sistemaweb.models.entities.Inspector;
import org.inicio.sistemaweb.repositories.InspectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InspectorServiceImpl implements InspectorService {

    @Autowired
    private InspectorRepository repository;
    @Override
    @Transactional(readOnly = true)
    public List<Inspector> findActiveOrders() {
        return repository.findByActivo(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inspector> findOrders() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inspector> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Inspector save(Inspector inspector) {
        return repository.save(inspector);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }


    @Override
    @Transactional
    public List<Inspector> findByDni(String dni) {
        return repository.findByDni(dni);
    }


    @Override
    @Transactional
    public List<Inspector> findByCadena(String name) {
        return repository.findByName(name);
    }



}
