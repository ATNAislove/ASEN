package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.PersonalZona;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonalZonaRowMapper implements RowMapper<PersonalZona> {
    @Override
    public PersonalZona mapRow(ResultSet rs, int rowNum) throws SQLException {
        PersonalZona personalZona = new PersonalZona();
        personalZona.setDni(rs.getString("dni"));
        personalZona.setNombre(rs.getString("nombre"));
        personalZona.setApellidos(rs.getString("apellidos"));
        personalZona.setFechaNacimiento(rs.getObject("fechaNacimiento", LocalDate.class));
        personalZona.setCorreoElectronico(rs.getString("correoElectronico"));
        personalZona.setDireccion(rs.getString("setDireccion"));
        personalZona.setMunicipio(rs.getString("municipio"));
        personalZona.setPais(rs.getString("pais"));
        personalZona.setTelefono(rs.getString("telefono"));
        return personalZona;
    }
}
