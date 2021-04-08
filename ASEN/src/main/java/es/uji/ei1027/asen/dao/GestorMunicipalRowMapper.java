package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.GestorMunicipal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class GestorMunicipalRowMapper implements RowMapper<GestorMunicipal> {
    public GestorMunicipal mapRow(ResultSet rs, int rowNum) throws SQLException {
        GestorMunicipal gestorMunicipal = new GestorMunicipal();
        gestorMunicipal.setDni(rs.getString("dni"));
        gestorMunicipal.setNombre(rs.getString("nombre"));
        gestorMunicipal.setApellidos (rs.getString("apellidos"));
        //Date fechaN = rs.getDate("fechaNacimiento");
        //gestorMunicipal.setFechaNacimiento(fechaN != null ? fechaN.toLocalDate() : null);
        gestorMunicipal.setFechaNacimiento (rs.getObject("fechaNacimiento", LocalDate.class));
        gestorMunicipal.setCorreoElectronico(rs.getString("correoElectronico"));
        gestorMunicipal.setDireccion (rs.getString("direccion "));
        gestorMunicipal.setMunicipioNacimiento (rs.getString("municipioNacimiento"));
        gestorMunicipal.setPais (rs.getString("pais"));
        gestorMunicipal.setTelefono (rs.getLong("telefono"));
        gestorMunicipal.setFechaRegistro (rs.getObject("fechaRegistro",LocalDate.class));
        gestorMunicipal.setFechaBaja (rs.getObject("fechaBaja",LocalDate.class));
        gestorMunicipal.setIdMunicipio (rs.getInt("idMunicipio"));
        return gestorMunicipal;
    }
}
