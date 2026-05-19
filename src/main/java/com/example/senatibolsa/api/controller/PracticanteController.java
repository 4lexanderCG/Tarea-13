package com.example.senatibolsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.senatibolsa.api.model.Practicante;
import com.example.senatibolsa.api.service.PracticanteService;

@RestController
@RequestMapping("/api/practicantes")
public class PracticanteController {
    @Autowired
    private PracticanteService practicanteService;
    public PracticanteController(PracticanteService practicanteService) {
        this.practicanteService = practicanteService;
    }

    @GetMapping
    public ResponseEntity<List<Practicante>> listar() {
        return ResponseEntity.ok(practicanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Practicante> obtenerPorId(@PathVariable Integer id) {
        return practicanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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
