package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Reserva;

import java.util.List;

public interface ReservaService {
    public List<Reserva> getReservasUsuario(String usuario);
    public List<FranjaHoraria> getFranjasHorarias();
    public String getHorasFranja(int idFranjaHoraria);
}
