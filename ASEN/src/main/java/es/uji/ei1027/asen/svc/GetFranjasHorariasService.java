package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;

import java.time.LocalDate;
import java.util.List;

public interface GetFranjasHorariasService {
    public List<FranjaHoraria> getFranjasHorarias();
    public String getHorasFranja(int idFranjaHoraria);
    public List<FranjaHoraria> getFranjasReserva();
    public List<FranjaHoraria> getFranjasServicio();
    public String transformarFecha(LocalDate fecha);
    public List<FranjaHoraria> getFranjaReservaLibres(String fecha,int idZona);
    public List<FranjaHoraria> getFranjasAccesible(int idArea, LocalDate fechaInicio);
    public List<FranjaHoraria> getFranjasAccesible(int idArea, LocalDate fechaInicio,LocalDate fechaFin);
    public List<FranjaHoraria> getFranjaReservaLibreReservable(String fecha, int idZona);
}
