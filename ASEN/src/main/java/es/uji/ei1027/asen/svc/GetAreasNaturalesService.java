package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.model.Municipio;

import java.util.List;

public interface GetAreasNaturalesService {
    public List<AreaNatural> getAreas();
    public String getNombreArea(int idArea);
    public int getIdArea(int idZona);
}
