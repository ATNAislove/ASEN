package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Accesibilidad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class AccesibilidadRowMapper implements RowMapper<Accesibilidad> {
    @Override
    public Accesibilidad mapRow(ResultSet rs, int i) throws SQLException {
        Accesibilidad accesibilidad = new Accesibilidad();
        accesibilidad.setIdFranjaHoraria(rs.getInt("idFranjaHoraria"));
        accesibilidad.setIdArea(rs.getInt("idArea"));
        accesibilidad.setFechaInicio(rs.getObject("fechaInicio", LocalDate.class));
        accesibilidad.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        return accesibilidad;
    }
}
