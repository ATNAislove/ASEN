package es.uji.ei1027.asen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Array;
import java.time.LocalDate;
import java.util.List;

public class FiltroOcupacion {
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    LocalDate fecha;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    LocalDate fechaFin;
    int idArea;
    int idFranjaHoraria;
    int idZona;
    int idReserva;
    List<Integer> zonas;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaFin() { return fechaFin; }

    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdReserva() { return idReserva; }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public List<Integer> getZonas() {
        return zonas;
    }

    public void setZonas(List<Integer> zonas) {
        this.zonas = zonas;
    }
}
