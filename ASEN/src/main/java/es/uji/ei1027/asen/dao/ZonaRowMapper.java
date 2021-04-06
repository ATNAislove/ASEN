package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Zona;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ZonaRowMapper implements RowMapper<Zona> {
    @Override
    public Zona mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zona zona = new Zona();
        zona.setIdCharNumero(rs.getString("idCharNumero"));
        zona.setNombreZona(rs.getString("nombreZona"));
        zona.setAforoMaximo(rs.getInt("aforoMaximo"));
        zona.setTipoTerreno(rs.getString("tipoTerreno"));
        return zona;
    }
}
