package com.example.senatibolsa.api.controller;
import com.example.senatibolsa.api.model.OfertaLaboral;
import com.example.senatibolsa.api.service.OfertaLaboralServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "*")
public class OfertaLaboralController {
    @Autowired
    private OfertaLaboralServicies ofertaServicies;

    @GetMapping
    public List<OfertaLaboral> listar() {
        return ofertaServicies.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaLaboral> buscarPorId(@PathVariable Integer id) {
        OfertaLaboral oferta = ofertaServicies.buscarPorId(id);
        if (oferta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oferta);
    }

    @PostMapping
    public ResponseEntity<OfertaLaboral> crear(@RequestBody OfertaLaboral oferta) {
        OfertaLaboral nuevaOferta = ofertaServicies.guardar(oferta);
        return new ResponseEntity<>(nuevaOferta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaLaboral> actualizar(@PathVariable Integer id, @RequestBody OfertaLaboral ofertaDatos) {
        OfertaLaboral ofertaExistente = ofertaServicies.buscarPorId(id);
        
        if (ofertaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        ofertaExistente.setTitulo(ofertaDatos.getTitulo());
        ofertaExistente.setEmpresa(ofertaDatos.getEmpresa());
        ofertaExistente.setCorreoEmpresa(ofertaDatos.getCorreoEmpresa());
        ofertaExistente.setDireccion(ofertaDatos.getDireccion());
        ofertaExistente.setDistrito(ofertaDatos.getDistrito());
        ofertaExistente.setDescripcionPuesto(ofertaDatos.getDescripcionPuesto());
        ofertaExistente.setNroVacantes(ofertaDatos.getNroVacantes());
        ofertaExistente.setActivo(ofertaDatos.getActivo());
        ofertaExistente.setFechaEstimacionContrato(ofertaDatos.getFechaEstimacionContrato());

        if (ofertaDatos.getRequerimientos() != null) {
            ofertaExistente.getRequerimientos().clear();
            ofertaDatos.getRequerimientos().forEach(req -> {
                req.setOfertaLaboral(ofertaExistente);
                ofertaExistente.getRequerimientos().add(req);
            });
        }

        OfertaLaboral ofertaActualizada = ofertaServicies.guardar(ofertaExistente);
        return ResponseEntity.ok(ofertaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        OfertaLaboral oferta = ofertaServicies.buscarPorId(id);
        if (oferta == null) {
            return ResponseEntity.notFound().build();
        }
        ofertaServicies.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }
}