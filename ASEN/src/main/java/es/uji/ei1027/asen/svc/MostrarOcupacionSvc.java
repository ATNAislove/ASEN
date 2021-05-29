package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.dao.MunicipioDao;
import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.model.Municipio;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MostrarOcupacionSvc implements MostrarOcupacionService{
    @Autowired
    private AreaNaturalDao areaNaturalDao;
    @Autowired
    private MunicipioDao municipioDao;


    @Override
    public String getMunicipiofromAreaNatural(int idArea){
        int municipioID = areaNaturalDao.getAreaNatural(idArea).getIdMunicipio();
        Municipio municipio = municipioDao.getMunicipio(municipioID);
        return municipio.getNombreMunicipio();
    }
}
