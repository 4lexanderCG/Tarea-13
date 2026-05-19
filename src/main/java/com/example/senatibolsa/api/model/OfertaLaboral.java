package com.example.senatibolsa.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "oferta_laboral")
@Data
public class OfertaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Integer idOferta;

    private String titulo;
    private String empresa;
    
    @Column(name = "correo_empresa")
    private String correoEmpresa;
    
    private String direccion;
    private String distrito;

    @Column(name = "descripcion_puesto", columnDefinition = "TEXT")
    private String descripcionPuesto;

    @Column(name = "nro_vacantes")
    private Integer nroVacantes = 1;

    private Boolean activo = true;

    @Column(name = "fecha_estimacion_contrato")
    private Date fechaEstimacionContrato;
    @OneToMany(mappedBy = "ofertaLaboral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Requerimiento> requerimientos;
}