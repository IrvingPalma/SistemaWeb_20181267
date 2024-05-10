package org.inicio.sistemaweb.controllers;

import org.inicio.sistemaweb.models.entities.Inspector;
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
@RequestMapping("/inspectores")
public class InspectorController {
    @Autowired
    private InspectorService service;

    @GetMapping
    public List<Inspector> list(){
        return service.findOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<Inspector> clienteOptional = service.findById(id);
        if(clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarInspector(@RequestBody Map<String, String> criterioBusqueda){

        String cadena=criterioBusqueda.get("cadena");
        System.out.println("Aqui viene codigo:");
        System.out.println(cadena);
        List<Inspector> inspectores = service.findByDni(cadena);

        if (inspectores == null || inspectores.isEmpty()) {
            // Si no se encontró por código, intenta buscar por nombre completo.
            inspectores = service.findByCadena("%" + cadena + "%");
        }

        if (inspectores != null && !inspectores.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", inspectores);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("reason", "inspector no encontrado");
            return ResponseEntity.ok(response);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Inspector inspector){
        Map<String, Object> response = new HashMap<>();

        List<Inspector> inspectores = service.findByDni(inspector.getDni());
        if (inspectores != null && !inspectores.isEmpty()) {
            response.put("success", false);
            response.put("reason","Codigo ya existente");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        inspector.setActivo(1);
        service.save(inspector);
        response.put("success", true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Inspector inspector,@PathVariable Long id){

        Optional<Inspector> o= service.findById(id);

        if(o.isPresent()) {
            Inspector inspectorDb=o.orElseThrow();



            inspectorDb.setDni(inspector.getDni());
            inspectorDb.setApellidoMaterno(inspector.getApellidoMaterno());
            inspectorDb.setApellidoPaterno(inspector.getApellidoPaterno());
            inspectorDb.setNombres(inspector.getNombres());
            inspectorDb.setCorreo(inspector.getCorreo());
            inspectorDb.setCelular(inspector.getCelular());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(inspectorDb));

        }

        return null;

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> remove(@RequestBody Inspector inspector){
        Long id=inspector.getId_inspector();

        Optional<Inspector> inspectorOptional = service.findById(id);
        if (inspectorOptional.isPresent()) {
            Inspector inspector2 = inspectorOptional.get();
            inspector2.setActivo(0);
            service.save(inspector2);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("reason", "inspector no encontrado");
            return ResponseEntity.ok(response);
        }

    }


















}
