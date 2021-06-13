package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Reserva;

import java.util.List;

public interface ReservaService {
    public List<Reserva> getReservasUsuario(String usuario);
    public void addOcupacion(int idReserva, int idZona);
    public void deleteOcupacion(int idReserva);
}
