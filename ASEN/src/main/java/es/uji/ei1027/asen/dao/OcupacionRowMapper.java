package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ocupacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OcupacionRowMapper implements RowMapper<Ocupacion> {
    @Override
    public Ocupacion mapRow(ResultSet rs, int i) throws SQLException {
        Ocupacion ocupacion = new Ocupacion();
        ocupacion.setIdReserva(rs.getInt("idReserva"));
        ocupacion.setIdZona(rs.getInt("idZona"));
        return ocupacion;
    }
}
