package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.DisponibilidadDao;
import es.uji.ei1027.asen.dao.ServicioDao;
import es.uji.ei1027.asen.dao.TipoServicioDao;
import es.uji.ei1027.asen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GetTiposServicioSvc implements GetTiposServicioService {
    @Autowired
    private TipoServicioDao tipoServicioDao;
    @Autowired
    private ServicioDao servicioDao;
    @Autowired
    private GetAreasNaturalesService getAreasNaturalesService;
    @Autowired
    private DisponibilidadDao disponibilidadDao;
    @Override
    public List<TipoServicio> getTiposServicioService() {
        return tipoServicioDao.getTipoServicios();
    }

    @Override
    public String getTipoServicio(int idTipoServicio) {
        return tipoServicioDao.getTipoServicio(idTipoServicio).getNombre();
    }
    @Override
    public List<Servicio> getServiciosMunicipio(int idMunicipio){
        List<AreaNatural> areas = getAreasNaturalesService.getAreasPueblo(idMunicipio);
        List<Servicio> servicios = new ArrayList<>();
        List<Servicio> auxiliar;
        for(AreaNatural area : areas){
            auxiliar = servicioDao.getServicioByArea(area.getIdArea());
            for(Servicio s :auxiliar){servicios.add(s);}
        }
        return servicios;
    }
    @Override
    public List<Servicio> getServiciosArea(int idArea){
        return servicioDao.getServicioByArea(idArea);
    }
    @Override
    public List<Disponibilidad> getHorariosServicio(int idServicio){
        return disponibilidadDao.getDisponibilidadesServicio(idServicio);
    }

}
