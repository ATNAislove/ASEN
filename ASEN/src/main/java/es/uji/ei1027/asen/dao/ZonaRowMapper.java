package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Zona;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ZonaRowMapper implements RowMapper<Zona> {
    @Override
    public Zona mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zona zona = new Zona();
        zona.setIdZona(rs.getInt("idZona"));
        zona.setIdCharNumero(rs.getString("idCharNum"));
        zona.setNombreZona(rs.getString("nombreZona"));
        zona.setAforoMaximo(rs.getInt("aforoMaximo"));
        zona.setTipoTerreno(rs.getString("tipoTerreno"));
        zona.setIdArea(rs.getInt("idArea"));
        return zona;
    }
}
