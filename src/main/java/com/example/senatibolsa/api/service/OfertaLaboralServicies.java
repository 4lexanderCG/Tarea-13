package com.example.senatibolsa.api.service;

import com.example.senatibolsa.api.model.OfertaLaboral;
import com.example.senatibolsa.api.repository.OfertaLaboralRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfertaLaboralServicies {
    @Autowired
    private OfertaLaboralRepositories ofertaRepositories;

    public List<OfertaLaboral> listarTodas() {
        return ofertaRepositories.findAll();
    }

    public OfertaLaboral buscarPorId(Integer id) {
        return ofertaRepositories.findById(id).orElse(null);
    }
    
    public OfertaLaboral guardar(OfertaLaboral oferta) {
        if (oferta.getRequerimientos() != null) {
            oferta.getRequerimientos().forEach(req -> req.setOfertaLaboral(oferta));
        }
        return ofertaRepositories.save(oferta);
    }

    public void eliminar(Integer id) {
        ofertaRepositories.deleteById(id);
    }
}