package es.uji.ei1027.asen.model;

import java.time.LocalTime;

public class FranjaHoraria {
    private int idFranjaHoraria;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int idArea;
    public FranjaHoraria(){
    }

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    @Override
    public String toString() {
        return "FranjaHoraria{" +
                "idFranjaHoraria=" + idFranjaHoraria +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", idArea=" + idArea +
                '}';
    }
}

