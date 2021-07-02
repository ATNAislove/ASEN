package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Reserva;
import es.uji.ei1027.asen.model.Zona;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    public List<Reserva> getReservasUsuario(String usuario);
    public String recuperarNombreZona(int idReserva);
    public String recuperarNombreArea(int idReserva);
    public String recuperarZonas(int idReserva);
    public boolean existeReserva(Reserva reserva);
    public int maxPersonas(int idArea);
    public int maxPersonas(List<Integer> zonas);
    public void insertarOcupacion(int idReserva, int idZona);
    public List<Zona> getZonas();
    public List<Zona> getZonas(int idArea);
    public List<Zona> getZonas(LocalDate fecha, int idFranjaHoraria, int idArea);
    public void borrarOcupaciones(int idReserva);
    public List<Integer> listaZonas(int idReserva);
    public int recuperarArea(int idReserva);
}
