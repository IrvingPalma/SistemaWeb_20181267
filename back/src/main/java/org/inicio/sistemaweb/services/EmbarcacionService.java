package org.inicio.sistemaweb.services;

import org.inicio.sistemaweb.models.entities.Embarcacion;

import java.util.List;
import java.util.Optional;

public interface EmbarcacionService {

    List<Embarcacion> findActiveOrders();
    List<Embarcacion> findOrders();
    Optional<Embarcacion> findById(Long id);

    Embarcacion save(Embarcacion embarcacion);

    void remove(Long id);

    List<Embarcacion> findByActivoAndMatricula(String matricula);

    List<Embarcacion> findByCadena(String nombres);


}
