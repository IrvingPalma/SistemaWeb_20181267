package org.inicio.sistemaweb.services;

import org.inicio.sistemaweb.models.entities.Embarcacion;
import org.inicio.sistemaweb.models.entities.Inspector;
import org.inicio.sistemaweb.repositories.EmbarcacionRepository;
import org.inicio.sistemaweb.repositories.InspectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmbarcacionServiceImpl implements EmbarcacionService {

    @Autowired
    private EmbarcacionRepository repository;
    @Override
    @Transactional(readOnly = true)
    public List<Embarcacion> findActiveOrders() {
        return repository.findByActivo(1);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Embarcacion> findOrders() {
        return repository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Embarcacion> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Embarcacion save(Embarcacion embarcacion) {
        return repository.save(embarcacion);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }


    @Override
    @Transactional
    public List<Embarcacion> findByActivoAndMatricula(String matricula) {
        return repository.findByMatricula(matricula);
    }


    @Override
    @Transactional
    public List<Embarcacion> findByCadena(String nombres) {
        return repository.findByName(nombres);
    }



}
