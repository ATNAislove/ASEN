package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.GestorMunicipalDao;
import es.uji.ei1027.asen.dao.MunicipioDao;
import es.uji.ei1027.asen.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetMunicipiosSvc implements GetMunicipiosService {
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private GestorMunicipalDao gestorMunicipalDao;
    @Override
    public List<Municipio> getMunicipios(){
        return municipioDao.getMunicipios();
    }
    @Override
    public int getMunicipioGestor(String user){
        return gestorMunicipalDao.getGestorMunicipalUser(user).getIdMunicipio();
    }
    @Override
    public Municipio getObjetoMunicipio(int idMunicipio){
        return municipioDao.getMunicipio(idMunicipio);
    }
}
