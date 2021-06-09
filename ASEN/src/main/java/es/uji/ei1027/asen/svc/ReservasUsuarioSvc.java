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
        return reservaDao.getReservas_usuario(usuario);
    }


}
