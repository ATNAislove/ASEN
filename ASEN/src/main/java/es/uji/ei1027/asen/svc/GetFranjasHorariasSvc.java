package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.AccesibilidadDao;
import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.dao.FranjaHorariaDao;
import es.uji.ei1027.asen.dao.ZonaDao;
import es.uji.ei1027.asen.model.Accesibilidad;
import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class GetFranjasHorariasSvc implements GetFranjasHorariasService{
    @Autowired
    private FranjaHorariaDao franjaHorariaDao;
    @Autowired
    private AccesibilidadDao accesibilidadDao;
    @Autowired
    private ZonaDao zonaDao;
    @Override
    public String getHorasFranja(int idFranjaHoraria){
        try {
            FranjaHoraria franja = franjaHorariaDao.getFranjaHoraria(idFranjaHoraria);
            if (franja == null) return " ";
            String resultado = franja.getHoraInicio().toString() + " - " + franja.getHoraFin().toString();
            return resultado;
        }catch(Exception e){
            return " ";
        }
    }
    @Override
    public List<FranjaHoraria> getFranjasHorarias(){
        return franjaHorariaDao.getFranjasHorarias();
    }
    @Override
    public List<FranjaHoraria> getFranjasReserva(){
        return franjaHorariaDao.getFranjasReserva();
    }
    @Override
    public List<FranjaHoraria> getFranjaReservaLibres(String fecha,int idZona){
        return franjaHorariaDao.getFranjasReserva(fecha,idZona);
    }
    @Override
    public List<FranjaHoraria> getFranjaReservaLibreReservable(String fecha, int idZona){
        List<FranjaHoraria> franjaslibres = getFranjaReservaLibres(fecha,idZona);
        List<Accesibilidad> horariosArea = accesibilidadDao.getAccesibilidadesArea(zonaDao.getZona(idZona).getIdArea(),fecha);
        List<FranjaHoraria> franjasArea = new ArrayList<>();
        List<FranjaHoraria> resultado = new ArrayList<>();
        for(Accesibilidad a : horariosArea){
            franjasArea.add(franjaHorariaDao.getFranjaHoraria(a.getIdFranjaHoraria()));
        }
        Iterator<FranjaHoraria> iter = franjaslibres.iterator();
        FranjaHoraria actual;
        while(iter.hasNext()){
            actual=iter.next();
            for(FranjaHoraria f : franjasArea){
                if(f.getHoraFin()==null && f.getHoraInicio().isBefore(actual.getHoraFin())){
                    resultado.add(actual);
                    break;
                }
                if(f.getHoraFin()!=null && (f.getHoraInicio().isBefore(actual.getHoraFin()) && f.getHoraFin().isAfter(actual.getHoraInicio()))){
                    resultado.add(actual);
                    break;
                }
            }
        }

        return resultado;
    }
    @Override
    public List<FranjaHoraria> getFranjasServicio(){
        return franjaHorariaDao.getFranjasServicio();
    }
    @Override
    public List<FranjaHoraria> getFranjasAccesible(int idArea, LocalDate fechaInicio){
        List<FranjaHoraria> franjasServicio = getFranjasServicio();
        List<FranjaHoraria> franjasOcupadas = new ArrayList<>();
        List<Accesibilidad> accesibilidadesArea = accesibilidadDao.getAccesibilidadesArea(idArea);
        for(Accesibilidad accesibilidad : accesibilidadesArea){
            //si las fechas de la nueva franja coincide con otra la añadimos a franjas ocupadas
            if(accesibilidad.getFechaFin()==null || (!accesibilidad.getFechaFin().isBefore(fechaInicio)))
                franjasOcupadas.add(franjaHorariaDao.getFranjaHoraria(accesibilidad.getIdFranjaHoraria()));
        }
        //eliminamos las franjas ya ocupadas
        for(FranjaHoraria f : franjasOcupadas){
            franjasServicio.remove(f);
        }
        //si hay alguna franja ocupada, comparamos y quitamos las franjas que coincidan para mostrar solo las disponibles

        if(franjasOcupadas.size()>0) {
            Iterator<FranjaHoraria> iter = franjasServicio.iterator();
            FranjaHoraria franja;
            while (iter.hasNext()) {
                franja = iter.next();
                for (FranjaHoraria franjaOc : franjasOcupadas) {
                    if ((!franjaOc.getHoraFin().isBefore(franja.getHoraInicio())) && (!franjaOc.getHoraInicio().isAfter(franja.getHoraFin()))) {
                        iter.remove();
                    }
                }
            }
        }
        return franjasServicio;
    }
    @Override
    public List<FranjaHoraria> getFranjasAccesible(int idArea, LocalDate fechaInicio,LocalDate fechaFin){
        List<FranjaHoraria> franjasServicio = getFranjasServicio();
        List<FranjaHoraria> franjasOcupadas = new ArrayList<>();
        List<Accesibilidad> accesibilidadesArea = accesibilidadDao.getAccesibilidadesArea(idArea);
        for(Accesibilidad accesibilidad : accesibilidadesArea){
            //si las fechas de la nueva franja coincide con otra la añadimos a franjas ocupadas
            if(accesibilidad.getFechaFin()==null && (!accesibilidad.getFechaInicio().isAfter(fechaFin)))
                franjasOcupadas.add(franjaHorariaDao.getFranjaHoraria(accesibilidad.getIdFranjaHoraria()));
            if(accesibilidad.getFechaFin()!=null && ((!accesibilidad.getFechaFin().isBefore(fechaInicio)) && (!accesibilidad.getFechaInicio().isAfter(fechaFin))))
                franjasOcupadas.add(franjaHorariaDao.getFranjaHoraria(accesibilidad.getIdFranjaHoraria()));
        }
        //eliminamos las franjas ya ocupadas
        for(FranjaHoraria f : franjasOcupadas){
            franjasServicio.remove(f);
        }
        //si hay alguna franja ocupada, comparamos y quitamos las franjas que coincidan para mostrar solo las disponibles
        if(franjasOcupadas.size()>0){
            Iterator<FranjaHoraria> iter = franjasServicio.iterator();
            FranjaHoraria franja;
            while(iter.hasNext()){
                franja = iter.next();
                for(FranjaHoraria franjaOc : franjasOcupadas){
                    if((!franjaOc.getHoraFin().isBefore(franja.getHoraInicio())) && (!franjaOc.getHoraInicio().isAfter(franja.getHoraFin()))){
                        iter.remove();
                    }
                }
            }
        }
        return franjasServicio;
    }

    @Override
    public String transformarFecha(LocalDate fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(fecha);
    }
}
