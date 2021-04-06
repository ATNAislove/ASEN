package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.AreaNatural;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AreaNaturalRowMapper implements RowMapper<AreaNatural> {
@Override
public AreaNatural mapRow(ResultSet rs, int rowNum) throws SQLException {
        AreaNatural areaNatural = new AreaNatural();
        areaNatural.setIdArea(rs.getInt("idArea"));
        areaNatural.setNombreArea(rs.getString("nombreArea"));
        areaNatural.setDescripcion(rs.getString("descripcion"));
        areaNatural.setImagen(rs.getString("imagen"));
        areaNatural.setTipoTerreno(rs.getString("tipoTerreno"));
        areaNatural.setTipoServicio(rs.getString("tipoServicio"));
        areaNatural.setInstalacion(rs.getString("instalacion"));
        areaNatural.setTipoArea(rs.getString("tipoArea"));
        areaNatural.setTipoAcceso(rs.getString("tipoAcceso"));
        return areaNatural;
        }
}
