package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.dao.FranjaHorariaDao;
import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetFranjasHorariasSvc implements GetFranjasHorariasService{
    @Autowired
    private FranjaHorariaDao franjaHorariaDao;
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
}
