package com.example.senatibolsa.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.senatibolsa.api.model.Practicante;
import com.example.senatibolsa.api.repository.PracticanteRepository;

@Service
public class PracticanteService {
    @Autowired
    private PracticanteRepository practicanteRepository;
    public PracticanteService(PracticanteRepository practicanteRepository) {
        this.practicanteRepository = practicanteRepository;
    }

    public List<Practicante> listarTodos() {
        return practicanteRepository.findAll();
    }

    public Optional<Practicante> buscarPorId(Integer id) {
        return practicanteRepository.findById(id);
    }

    public Practicante guardar(Practicante practicante) {
        if (practicante.getDni() == null || practicante.getDni().length() != 8) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos.");
        }
        return practicanteRepository.save(practicante);
    }
}
