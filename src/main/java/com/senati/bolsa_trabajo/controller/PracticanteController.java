package com.senati.bolsa_trabajo.controller;

import com.senati.bolsa_trabajo.model.Practicante;
import com.senati.bolsa_trabajo.service.PracticanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/practicantes")
@CrossOrigin(origins = "http://localhost:3000")
public class PracticanteController {

    private final PracticanteService practicanteService;

    public PracticanteController(PracticanteService practicanteService) {
        this.practicanteService = practicanteService;
    }

    // GET http://localhost:8080/api/practicantes
    @GetMapping
    public ResponseEntity<List<Practicante>> listar() {
        return ResponseEntity.ok(practicanteService.listarTodos());
    }

    // GET http://localhost:8080/api/practicantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Practicante> obtenerPorId(@PathVariable Integer id) {
        return practicanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST http://localhost:8080/api/practicantes
    @PostMapping
    public ResponseEntity<Practicante> registrar(@RequestBody Practicante practicante) {
        try {
            Practicante nuevoPracticante = practicanteService.guardar(practicante);
            return ResponseEntity.ok(nuevoPracticante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}