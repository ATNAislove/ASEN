package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.*;
import es.uji.ei1027.asen.model.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class MostrarOcupacionSvc implements MostrarOcupacionService{
    @Autowired
    private AreaNaturalDao areaNaturalDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ZonaDao zonaDao;
    @Autowired
    private ReservaDao reservaDao;
    @Autowired
    private FranjaHorariaDao franjaHorariaDao;

    @Override
    public String getMunicipiofromAreaNatural(int idArea){
        int municipioID = areaNaturalDao.getAreaNatural(idArea).getIdMunicipio();
        Municipio municipio = municipioDao.getMunicipio(municipioID);
        return municipio.getNombreMunicipio();
    }
    public List<Zona> getZonasLibresByfecha(String fecha, int idArea){
        List<Zona> zonasOcupadas = zonaDao.getZonasOcupadasByFecha(fecha, idArea);
        List<Zona> zonasArea = zonaDao.getZonas();
        for(Zona z : zonasOcupadas){
            if(zonasArea.contains(z)){
                zonasArea.remove(z);
            }
        }
        return zonasArea;
    }
    public int getNumZonasLibresByFecha(String fecha, int idArea){
        return getZonasLibresByfecha(fecha,idArea).size();
    }
    //obtiene el numero de personas que han ocupado un area una fecha determinada
    public int getPlazasOcupadasByFecha(String fecha, int idArea){
        List<Reserva> reservas = reservaDao.getReservasByFecha(fecha, idArea);
        int ocupacion = 0;
        for(Reserva r : reservas){
            ocupacion += r.getNumeroPersonas();
        }
        return ocupacion;
    }
    public float getporcentajeOcupacion(String fecha, int idArea){
        int plazasOcupadas = getPlazasOcupadasByFecha(fecha, idArea);
        int plazasTotales = areaNaturalDao.getCapacidadTotal(idArea);
        return ((plazasTotales-plazasOcupadas)/plazasTotales)*100;
    }
    public List<FranjaHoraria> getFranjasHorarias(){
        return franjaHorariaDao.getFranjasHorarias();
    }
}
