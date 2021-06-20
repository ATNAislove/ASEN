package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.*;
import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Reserva> getReservasUsuario(String usuario) {return reservaDao.getReservasUsuario(usuario);}

    @Override
    public String recuperarNombreZona(int idZona){
        return zonaDao.getZona(idZona).getNombreZona();
    }
    @Override
    public String recuperarIdCharNum(int idZona){
        return zonaDao.getZona(idZona).getIdCharNumero();
    }
    @Override
    public String recuperarNombreArea(int idZona){
        int idArea = zonaDao.getZona(idZona).getIdArea();
        return areaNaturalDao.getAreaNatural(idArea).getNombreArea();
    }
    public boolean existeReserva(Reserva reserva){
        if(reservaDao.existeReserva(reserva).size()>0)
            return true;
        return false;
    }
}
