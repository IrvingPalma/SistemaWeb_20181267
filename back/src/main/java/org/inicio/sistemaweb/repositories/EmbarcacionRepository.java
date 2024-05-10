package org.inicio.sistemaweb.repositories;

import org.inicio.sistemaweb.models.entities.Embarcacion;
import org.inicio.sistemaweb.models.entities.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface EmbarcacionRepository extends JpaRepository<Embarcacion,Long>  {

    List<Embarcacion> findByMatricula(String matricula);

    @Query("SELECT c FROM Embarcacion c WHERE CONCAT(c.nombres) LIKE :name")
    List<Embarcacion> findByName( @Param("name") String nombres);
    List<Embarcacion> findByActivo(int activo);
    List<Embarcacion> findAll();



}
