package com.senati.bolsa_trabajo.service;

import com.senati.bolsa_trabajo.model.Practicante;
import com.senati.bolsa_trabajo.repository.PracticanteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PracticanteService {

    private final PracticanteRepository practicanteRepository;

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