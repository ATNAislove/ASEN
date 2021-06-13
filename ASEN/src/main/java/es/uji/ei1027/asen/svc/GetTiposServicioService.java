package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.Disponibilidad;
import es.uji.ei1027.asen.model.Servicio;
import es.uji.ei1027.asen.model.TipoServicio;

import java.util.List;

public interface GetTiposServicioService {
    public List<TipoServicio> getTiposServicioService();
    public String getTipoServicio(int idTipoServicio);
    public List<Servicio> getServiciosMunicipio(int idMunicipio);
    public List<Servicio> getServiciosArea(int idArea);
    public List<Disponibilidad> getHorariosServicio(int IdServicio);
}
