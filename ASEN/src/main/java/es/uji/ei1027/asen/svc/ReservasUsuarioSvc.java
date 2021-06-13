package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.FranjaHorariaDao;
import es.uji.ei1027.asen.dao.OcupacionDao;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Ocupacion;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class ReservasUsuarioSvc implements ReservaService {

    //CiudadanoDao ciudadanoDao;
    @Autowired
    private FranjaHorariaDao franjaHorariaDao;
    @Autowired
    private ReservaDao reservaDao;
    @Autowired
    private OcupacionDao ocupacionDao;

    public List<Reserva> getReservasUsuario(String usuario) {return reservaDao.getReservasUsuario(usuario);}


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
    public List<FranjaHoraria> getFranjasHorarias(){
        return franjaHorariaDao.getFranjasHorarias();
    }
    public void addOcupacion(int idReserva, int idZona){
        Ocupacion ocupacion = new Ocupacion();
        ocupacion.setIdReserva(idReserva);
        ocupacion.setIdZona(idZona);
        ocupacionDao.addOcupacio(ocupacion);
    }
    public void deleteOcupacion(int idReserva){
        ocupacionDao.deleteOcupacio(idReserva);
    }
}
