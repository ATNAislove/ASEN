package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Zona;

import java.util.List;

public interface MostrarOcupacionService {
    public String getMunicipiofromAreaNatural(int idArea);
    public List<Zona> getZonasLibresByfecha(String fecha, int idArea);
    public int getNumZonasLibresByFecha(String fecha, int idArea,int idFranjaHoraria);
    public int ocupacionDiaArea(String fecha, int idArea,int idFranjaHoraria);
    public float getPorcentajeOcupacion(String fecha, int idArea,int idFranjaHoraria);
    public int capacidadTotalArea(int idArea);
    public int capacidadTotalAreaDiaria(int idArea);
}
