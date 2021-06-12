package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.model.AreaNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAreasNaturalesSvc implements  GetAreasNaturalesService {
    @Autowired
    private AreaNaturalDao areaNaturalDao;

    @Override
    public List<AreaNatural> getAreas() {
        return  areaNaturalDao.getAreasNaturales();
    }
}
