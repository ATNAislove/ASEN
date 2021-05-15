package es.uji.ei1027.asen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Servicio {
    private int idServicio;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaFin;
    private int periodicidad;
    private String tipoTemporada;
    private int idTipoServicio;
    private int idArea;

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

    public int getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(int periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getTipoTemporada() {
        return tipoTemporada;
    }

    public void setTipoTemporada(String tipoTemporada) {
        this.tipoTemporada = tipoTemporada;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", periodicidad=" + periodicidad +
                ", tipoTemporada='" + tipoTemporada + '\'' +
                ", idTipoServicio=" + idTipoServicio +
                ", idArea=" + idArea +
                '}';
    }
}
