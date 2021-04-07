package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.Ocupacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class FranjaHorariaRowMapper implements RowMapper<FranjaHoraria> {
    @Override
    public FranjaHoraria mapRow(ResultSet rs, int i) throws SQLException {
        FranjaHoraria franjaHoraria = new FranjaHoraria();
        franjaHoraria.setIdFranjaHoraria(rs.getInt("idReserva"));
        franjaHoraria.setHoraInicio(rs.getObject("horaInicio",LocalTime.class));
        franjaHoraria.setHoraFin(rs.getObject("horaFin",LocalTime.class));
        franjaHoraria.setIdArea(rs.getInt("idArea"));
        return franjaHoraria;
    }
}