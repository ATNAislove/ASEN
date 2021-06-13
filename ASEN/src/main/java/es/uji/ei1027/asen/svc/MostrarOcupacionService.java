package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Zona;

import java.util.List;

public interface MostrarOcupacionService {
    public String getMunicipiofromAreaNatural(int idArea);
    public List<Zona> getZonasLibresByfecha(String fecha, int idArea);
    public int getPlazasOcupadasByFecha(String fecha, int idArea);
}
