package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Servicio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ServicioRowMapper implements RowMapper<Servicio> {
    @Override
    public Servicio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("idServicio"));
        servicio.setFechaInicio(rs.getObject("fechaInicio", LocalDate.class));
        servicio.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        servicio.setPeriodicidad(rs.getInt("periodicidad"));
        servicio.setTipoTemporada(rs.getString("tipoTemporada"));
        servicio.setIdTipoServicio(rs.getInt("idTipoServicio"));
        servicio.setIdArea(rs.getInt("idArea"));
        return servicio;
    }
}
