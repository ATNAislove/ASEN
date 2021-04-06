package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Municipio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MunicipioRowMapper implements RowMapper<Municipio>{
        public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(rs.getInt("idMunicipio"));
            municipio.setNombreMunicipio(rs.getString("nombreMunicipio"));
            municipio.setCodigoPostal (rs.getInt("codigoPostal"));
            municipio.setNombreProvincia (rs.getString("nombreProvincia"));
            municipio.setPoblacion(rs.getInt("poblacion"));
            return municipio;
        }

}
