package org.inicio.sistemaweb.repositories;

import org.inicio.sistemaweb.models.entities.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface InspectorRepository extends JpaRepository<Inspector,Long>  {

    List<Inspector> findByDni(String dni);

    @Query("SELECT c FROM Inspector c WHERE  CONCAT(c.nombres, ' ', c.apellidoPaterno, ' ', c.apellidoMaterno) LIKE :name")
    List<Inspector> findByName( @Param("name") String name);
    List<Inspector> findByActivo(int activo);

    List<Inspector> findAll();



}
