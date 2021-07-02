package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.*;
import es.uji.ei1027.asen.model.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
    @Autowired
    private OcupacionDao ocupacionDao;

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
            List<Ocupacion> ocupaciones = new ArrayList<>();
            List<Reserva> reservas = reservaDao.getReservasByFecha(fecha,idArea);
            for(Reserva r : reservas){
                ocupaciones.addAll(ocupacionDao.getOcupaciones(r.getIdReserva()));
            }
            zonasOcupadas = ocupaciones.size();
            return (zonaDao.numZonas(idArea)*numFranjasHorariasCiudadano())-zonasOcupadas;
        }else{
            List<Ocupacion> ocupaciones = new ArrayList<>();

            FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
            List<Reserva> reservas =  reservaDao.getReservasByFecha(fecha,idArea,franja.getHoraInicio(),franja.getHoraFin());
            for(Reserva r : reservas){
                ocupaciones.addAll(ocupacionDao.getOcupaciones(r.getIdReserva()));
            }
            zonasOcupadas = ocupaciones.size();
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
        Integer resultado = areaNaturalDao.getCapacidadTotal(idArea);
        if(resultado==null)
            return 0;
        return resultado;
    }
    @Override
    public int capacidadTotalAreaDiaria(int idArea){
        return capacidadTotalArea(idArea)*numFranjasHorariasCiudadano();
    }
    public int capacidadTotalAreaFranja(int idArea, int idFranjaHoraria){
        if(idFranjaHoraria<0) return capacidadTotalAreaDiaria(idArea);
        FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
        return capacidadTotalArea(idArea)*franjaHorariaDao.getFranjasReserva(franja.getHoraInicio(),franja.getHoraFin()).size();
    }
    public int numFranjasHorariasCiudadano(){
        return franjaHorariaDao.getFranjasReserva().size();
    }

}
