package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class FranjaHorariaRowMapper implements RowMapper<FranjaHoraria> {
    public FranjaHoraria mapRow(ResultSet rs, int i) throws SQLException {
        FranjaHoraria franjaHoraria = new FranjaHoraria();
        franjaHoraria.setIdFranjaHoraria(rs.getInt("idFranjaHoraria"));
        franjaHoraria.setHoraInicio(LocalTime.parse(rs.getString("horaInicio")));
        franjaHoraria.setHoraFin(LocalTime.parse(rs.getString("horaFin")));
        return franjaHoraria;
    }
}