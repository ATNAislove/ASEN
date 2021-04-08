package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CiudadanoRowMapper implements RowMapper<Ciudadano> {
    public Ciudadano mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setUsuario(rs.getString("usuario"));
        ciudadano.setCodigoCiudadano(rs.getString("codigoCiudadano"));
        ciudadano.setPin(rs.getString("pin"));
        ciudadano.setFechaNacimiento(rs.getObject("fechaNacimiento",LocalDate.class));
        ciudadano.setNombre(rs.getString("nombre"));
        ciudadano.setApellidos(rs.getString("apellidos"));
        ciudadano.setDireccion(rs.getString("direccion"));
        ciudadano.setCorreoElectronico(rs.getString("correoElectronico"));
        ciudadano.setMunicipio(rs.getString("municipio"));
        ciudadano.setPais(rs.getString("pais"));
        ciudadano.setFechaRegistro(rs.getObject("fechaRegistro",LocalDate.class));
        ciudadano.setFechaBaja(rs.getObject("fechaBaja", LocalDate.class));
        return ciudadano;
    }
}
