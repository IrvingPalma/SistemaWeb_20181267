package org.inicio.sistemaweb.controllers;

import org.inicio.sistemaweb.models.entities.Embarcacion;
import org.inicio.sistemaweb.models.entities.Inspector;
import org.inicio.sistemaweb.services.EmbarcacionService;
import org.inicio.sistemaweb.services.InspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/embarcaciones")
public class EmbarcacionController {
    @Autowired
    private EmbarcacionService service;

    @GetMapping
    public List<Embarcacion> list(){
        return service.findOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<Embarcacion> clienteOptional = service.findById(id);
        if(clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarEmbarcacion(@RequestBody Map<String, String> criterioBusqueda){

        String cadena=criterioBusqueda.get("cadena");
        System.out.println("Aqui viene codigo:");
        System.out.println(cadena);
        List<Embarcacion> embarcaciones = service.findByActivoAndMatricula(cadena);

        if (embarcaciones == null || embarcaciones.isEmpty()) {
            // Si no se encontró por código, intenta buscar por nombre completo.
            embarcaciones = service.findByCadena("%" + cadena + "%");
        }

        if (embarcaciones != null && !embarcaciones.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", embarcaciones);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("reason", "embarcacion no encontrada");
            return ResponseEntity.ok(response);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Embarcacion embarcacion){
        Map<String, Object> response = new HashMap<>();

        List<Embarcacion> embarcaciones = service.findByActivoAndMatricula(embarcacion.getMatricula());
        if (embarcaciones != null && !embarcaciones.isEmpty()) {
            response.put("success", false);
            response.put("reason","Matricula ya existente");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        embarcacion.setActivo(1);
        service.save(embarcacion);
        response.put("success", true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Embarcacion embarcacion,@PathVariable Long id){

        Optional<Embarcacion> o= service.findById(id);

        if(o.isPresent()) {
            Embarcacion embarcacionDb=o.orElseThrow();



            embarcacionDb.setCarga(embarcacion.getCarga());
            embarcacionDb.setMatricula(embarcacion.getMatricula());
            embarcacionDb.setNombres(embarcacion.getNombres());
            embarcacionDb.setTripulacion(embarcacion.getTripulacion());
            embarcacionDb.setCelular(embarcacion.getCelular());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(embarcacionDb));

        }

        return null;

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> remove(@RequestBody Embarcacion embarcacion){
        Long id=embarcacion.getId_embarcacion();

        Optional<Embarcacion> embarcacionOptional = service.findById(id);
        if (embarcacionOptional.isPresent()) {
            Embarcacion embarcacion2 = embarcacionOptional.get();
            embarcacion2.setActivo(0);
            service.save(embarcacion2);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("reason", "embarcacion no encontrada");
            return ResponseEntity.ok(response);
        }

    }


















}
