package es.uji.ei1027.asen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Accesibilidad {
    private int idFranjaHoraria;
    private int idArea;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;

    public Accesibilidad(){
    }

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Accesibilidad{" +
                "idFranjaHoraria=" + idFranjaHoraria +
                ", idArea=" + idArea +
                ", fechaInicio= "+fechaInicio+
                ", fechaFin="+fechaFin+
                '}';
    }

}
