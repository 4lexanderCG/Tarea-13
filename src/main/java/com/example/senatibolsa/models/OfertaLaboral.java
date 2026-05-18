package com.example.senatibolsa.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "oferta_laboral")
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

    public OfertaLaboral() {
    }

    public OfertaLaboral(Integer idOferta, String titulo, String empresa, String correoEmpresa, String direccion, String distrito, String descripcionPuesto, Integer nroVacantes, Boolean activo, Date fechaEstimacionContrato, List<Requerimiento> requerimientos) {
        this.idOferta = idOferta;
        this.titulo = titulo;
        this.empresa = empresa;
        this.correoEmpresa = correoEmpresa;
        this.direccion = direccion;
        this.distrito = distrito;
        this.descripcionPuesto = descripcionPuesto;
        this.nroVacantes = nroVacantes;
        this.activo = activo;
        this.fechaEstimacionContrato = fechaEstimacionContrato;
        this.requerimientos = requerimientos;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    public Integer getNroVacantes() {
        return nroVacantes;
    }

    public void setNroVacantes(Integer nroVacantes) {
        this.nroVacantes = nroVacantes;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaEstimacionContrato() {
        return fechaEstimacionContrato;
    }

    public void setFechaEstimacionContrato(Date fechaEstimacionContrato) {
        this.fechaEstimacionContrato = fechaEstimacionContrato;
    }

    public List<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

}
