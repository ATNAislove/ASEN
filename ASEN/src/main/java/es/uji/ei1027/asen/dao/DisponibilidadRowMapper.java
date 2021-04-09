package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Disponibilidad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisponibilidadRowMapper implements RowMapper<Disponibilidad> {
    public Disponibilidad mapRow(ResultSet rs, int rowNum) throws SQLException {
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setIdFranjaHoraria(rs.getInt("idFranjaHoraria"));
        disponibilidad.setIdServicio(rs.getInt("idServicio"));
        return disponibilidad;
    }
}
