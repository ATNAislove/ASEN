package es.uji.ei1027.asen.svc;

import es.uji.ei1027.asen.model.FranjaHoraria;

import java.time.LocalDate;
import java.util.List;

public interface GetFranjasHorariasService {
    public List<FranjaHoraria> getFranjasHorarias();
    public String getHorasFranja(int idFranjaHoraria);
    public String transformarFecha(LocalDate fecha);
}
