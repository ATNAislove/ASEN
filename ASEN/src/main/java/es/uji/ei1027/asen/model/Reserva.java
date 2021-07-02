package es.uji.ei1027.asen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Reserva {
    private int idReserva;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    private int numeroPersonas;
    private LocalTime horaSalida;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    private String codigoQR;
    private String estadoReserva;
    private String usuario;
    private int idFranjaHoraria;
    private List<Integer> zonas;

    public Reserva() {
    }

    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }


    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public void setHoraSalida(LocalTime fechaSalida) {
        this.horaSalida = fechaSalida;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public List<Integer> getZonas() {
        return zonas;
    }

    public void setZonas(List<Integer> zonas) {
        this.zonas = zonas;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", fecha=" + fecha +
                ", numeroPersonas=" + numeroPersonas +
                ", fechaSalida=" + horaSalida +
                ", fechaCreacion=" + fechaCreacion +
                ", codigoQR='" + codigoQR + '\'' +
                ", estadoReserva='" + estadoReserva + '\'' +
                ", usuario='" + usuario + '\'' +
                ", idFranjaHoraria=" + idFranjaHoraria +
                ", zonas="+zonas.toString()+
                '}';
    }
}
