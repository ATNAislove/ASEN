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
    @Override
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
    @Override
    public int getNumZonasLibresByFecha(String fecha, int idArea,int idFranjaHoraria){
        int zonasOcupadas;
        if(idFranjaHoraria<0){
            zonasOcupadas = reservaDao.getReservasByFecha(fecha,idArea).size();
            return (zonaDao.numZonas(idArea)*numFranjasHorariasCiudadano())-zonasOcupadas;
        }
        else{
            FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
            zonasOcupadas = reservaDao.getReservasByFecha(fecha,idArea,franja.getHoraInicio(),franja.getHoraFin()).size();
            return (zonaDao.numZonas(idArea)*franjaHorariaDao.getFranjasReserva(franja.getHoraInicio(),franja.getHoraFin()).size())-zonasOcupadas;
        }


    }
    //obtiene el numero de personas que han ocupado un area una fecha determinada
    @Override
    public int ocupacionDiaArea(String fecha, int idArea, int idFranjaHoraria){
        List<Reserva> reservas;
        if(idFranjaHoraria<0){
             reservas = reservaDao.getReservasByFecha(fecha, idArea);
        }else{
            FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
            reservas = reservaDao.getReservasByFecha(fecha,idArea,franja.getHoraInicio(),franja.getHoraFin());
        }


        int ocupacion = 0;
        for(Reserva r : reservas){
            ocupacion += r.getNumeroPersonas();
        }
        return ocupacion;
    }
    @Override
    public float getPorcentajeOcupacion(String fecha, int idArea,int idFranjaHoraria){
        int plazasOcupadas = ocupacionDiaArea(fecha, idArea, idFranjaHoraria);
        int plazasTotales = capacidadTotalAreaFranja(idArea,idFranjaHoraria);
        return (float)Math.round(((float)plazasOcupadas/plazasTotales)*10000)/100;
    }
    @Override
    public int capacidadTotalArea(int idArea){
        return areaNaturalDao.getCapacidadTotal(idArea);
    }
    @Override
    public int capacidadTotalAreaDiaria(int idArea){
        return areaNaturalDao.getCapacidadTotal(idArea)*numFranjasHorariasCiudadano();
    }
    public int capacidadTotalAreaFranja(int idArea, int idFranjaHoraria){
        if(idFranjaHoraria<0) return capacidadTotalAreaDiaria(idArea);
        FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
        return areaNaturalDao.getCapacidadTotal(idArea)*franjaHorariaDao.getFranjasReserva(franja.getHoraInicio(),franja.getHoraFin()).size();
    }
    public int numFranjasHorariasCiudadano(){
        return franjaHorariaDao.getFranjasReserva().size();
    }

}
