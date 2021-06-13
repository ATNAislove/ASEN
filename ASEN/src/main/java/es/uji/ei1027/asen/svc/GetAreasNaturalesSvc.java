package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.dao.ZonaDao;
import es.uji.ei1027.asen.model.AreaNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAreasNaturalesSvc implements  GetAreasNaturalesService {
    @Autowired
    private AreaNaturalDao areaNaturalDao;
    @Autowired
    private ZonaDao zonaDao;

    @Override
    public List<AreaNatural> getAreas() {
        return  areaNaturalDao.getAreasNaturales();
    }

    @Override
    public String getNombreArea(int idArea) {
        return areaNaturalDao.getAreaNatural(idArea).getNombreArea();
    }
    @Override
    public int getIdArea(int idZona){
        return zonaDao.getZona(idZona).getIdArea();
    }
    @Override
    public List<AreaNatural> getAreasPueblo(int idMunicipio) {
        return  areaNaturalDao.getAreasPorMunicipio(idMunicipio);
    }


}
