package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.TipoServicioDao;
import es.uji.ei1027.asen.model.TipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetTiposServicioSvc implements GetTiposServicioService {
    @Autowired
    private TipoServicioDao tipoServicioDao;
    @Override
    public List<TipoServicio> getTiposServicioService() {
        return tipoServicioDao.getTipoServicios();
    }
}
