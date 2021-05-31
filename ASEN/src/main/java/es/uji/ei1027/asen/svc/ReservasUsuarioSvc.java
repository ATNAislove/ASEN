package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservasUsuarioSvc implements getReservasUsuarios {
    ReservaDao reservaDao;
    CiudadanoDao ciudadanoDao;

    public List<Reserva> getReservasUsuario(String usuario) {
        List<Reserva> classReserva = reservaDao.getReservas(); //todas las reserva
        //modificar con session
        Ciudadano ciudadano = ciudadanoDao.getCiudadano(usuario); //usuario que tengo
        List<Reserva> res = new LinkedList<>();
        for (Reserva rsv : classReserva) {
            if(rsv.getUsuario().equals(usuario)){
                res.add(rsv);
            }
        }
        return res;
    }
}
