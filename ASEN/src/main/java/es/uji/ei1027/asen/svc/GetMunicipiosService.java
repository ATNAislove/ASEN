package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.Municipio;
import org.springframework.stereotype.Service;

import java.util.List;
public interface GetMunicipiosService {
    public List<Municipio> getMunicipios();
    public int getMunicipioGestor(String user);
    public Municipio getObjetoMunicipio(int idMunicipio);
}
