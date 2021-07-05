package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.*;
import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Ocupacion;
import es.uji.ei1027.asen.model.Reserva;
import es.uji.ei1027.asen.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class ReservasUsuarioSvc implements ReservaService {

    @Autowired
    private ReservaDao reservaDao;

    @Autowired
    private AreaNaturalDao areaNaturalDao;
    @Autowired
    private ZonaDao zonaDao;
    @Autowired
    private OcupacionDao ocupacionDao;

    public List<Reserva> getReservasUsuario(String usuario) {return reservaDao.getReservasUsuario(usuario);}

    @Override
    public String recuperarNombreZona(int idZona){
        return zonaDao.getZona(idZona).getIdCharNumero();
    }
    @Override
    public String recuperarZonas(int idReserva){
        List<Ocupacion> ocupaciones = ocupacionDao.getOcupaciones(idReserva);
        String res = "";
        for(Ocupacion o : ocupaciones){
            res += zonaDao.getZona(o.getIdZona()).getIdCharNumero() + ", ";
        }
        if(res.length()>0)
            res = res.substring(0,res.length()-2);
        return res;
    }
    public List<Integer> listaZonas(int idReserva){
        List<Ocupacion> ocupaciones = ocupacionDao.getOcupaciones(idReserva);
        List<Integer> lista = new ArrayList<>();
        for(Ocupacion o : ocupaciones){
            lista.add(o.getIdZona());
        }
        return lista;
    }
    @Override
    public String recuperarNombreArea(int idReserva){
        List<Ocupacion> ocupaciones = ocupacionDao.getOcupaciones(idReserva);
        if(ocupaciones.size()>0){
            return areaNaturalDao.getAreaNatural(zonaDao.getZona(ocupaciones.get(0).getIdZona()).getIdArea()).getNombreArea();
        }
        return "";
    }
    @Override
    public int recuperarArea(int idReserva){
        List<Ocupacion> ocupaciones = ocupacionDao.getOcupaciones(idReserva);
        if(ocupaciones.size()>0){
            return zonaDao.getZona(ocupaciones.get(0).getIdZona()).getIdArea();
        }
        return -1;
    }

    public int maxPersonas(int idArea){
        return zonaDao.getMaximoPersonasArea(idArea);
    }
    public int maxPersonas(List<Integer> zonas){
        int resultado = 0;
        for(int i : zonas)
            resultado += zonaDao.getMaximoPersonas(i);
        return resultado;
    }

    public void insertarOcupacion(int idReserva, int idZona){
        ocupacionDao.addOcupacio(idReserva,idZona);
    }
    public void borrarOcupaciones(int idReserva){ocupacionDao.deleteOcupacio(idReserva);}
    @Autowired
    public List<Zona> getZonas(){
        return zonaDao.getZonas();
    }
    public List<Zona> getZonas(int idArea){
        return zonaDao.getZonas(idArea);
    }
    public List<Zona> getZonas(LocalDate fecha, int idFranjaHoraria, int idArea){
        return zonaDao.getZonas(fecha.toString(),idFranjaHoraria,idArea);
    }
}
