package org.inicio.sistemaweb.services;

import org.inicio.sistemaweb.models.entities.Inspector;

import java.util.List;
import java.util.Optional;

public interface InspectorService {

    List<Inspector> findActiveOrders();

    List<Inspector> findOrders();

    Optional<Inspector> findById(Long id);

    Inspector save(Inspector inspector);

    void remove(Long id);

    List<Inspector> findByDni(String dni);

    List<Inspector> findByCadena(String name);


}
