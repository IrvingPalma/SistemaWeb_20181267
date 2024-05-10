package org.inicio.sistemaweb.models.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "embarcacion")
public class Embarcacion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_embarcacion;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "nombres")
    private String nombres;


    @Column(name = "celular")
    private String celular;

    @Column(name = "tripulacion")
    private int tripulacion;

    @Column(name = "carga")
    private double carga;

    @Column(name="activo")
    private int activo;

    public Long getId_embarcacion() {
        return id_embarcacion;
    }

    public void setId_embarcacion(Long id_embarcacion) {
        this.id_embarcacion = id_embarcacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getTripulacion() {
        return tripulacion;
    }

    public void setTripulacion(int tripulacion) {
        this.tripulacion = tripulacion;
    }

    public double getCarga() {
        return carga;
    }

    public void setCarga(double carga) {
        this.carga = carga;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
