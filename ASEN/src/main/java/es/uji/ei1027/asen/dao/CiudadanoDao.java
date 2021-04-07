package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CiudadanoDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el Ciudadano a la base de dades */

    public void addCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("INSERT INTO ciudadano VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",ciudadano.getUsuario(), ciudadano.getCodigoCiudadano(),ciudadano.getPin(),
                ciudadano.getFechaNacimiento(),ciudadano.getNombre(),ciudadano.getApellidos(),ciudadano.getDireccion(),ciudadano.getCorreoElectronico(),ciudadano.getMunicipio()
        ,ciudadano.getPais(),ciudadano.getPais(),ciudadano.getFechaRegistro(),ciudadano.getFechaBaja());
    }
    /* Esborra el ciudadano de la base de dades */
    public void deleteCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("DELETE FROM ciudadano WHERE usuario=?",ciudadano.getUsuario());
    }

    public void deleteCiudadano(String usuario) {
        jdbcTemplate.update("DELETE FROM ciudadano WHERE usuario=?",usuario);
    }

    /* Actualitza els atributs del ciudadano
       (excepte el usuario, que és la clau primària) */
    public void updateCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("UPDATE ciudadano SET codigoCiudadano=?, pin=?, fechaNacimiento=?, nombre=?, " +
                        "apellidos=?, direccion=?, correoElectronico=?, municipio=?, pais=?, fechaRegistro=?," +
                        "fechaBaja=? WHERE idMunicipio=?", ciudadano.getCodigoCiudadano(),ciudadano.getPin(),
                ciudadano.getFechaNacimiento(),ciudadano.getNombre(), ciudadano.getApellidos(),
                ciudadano.getDireccion(),ciudadano.getCorreoElectronico(),ciudadano.getMunicipio(),ciudadano.getPais(),
                ciudadano.getFechaRegistro(),ciudadano.getFechaBaja(), ciudadano.getMunicipio());
    }
    /* Obté el ciudadano amb el usuario donat. Torna null si no existeix. */
    public Ciudadano getCiudadano(String usuario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Ciudadano WHERE usuario = '"+ usuario + "'", new CiudadanoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els ciudadanos. Torna una llista buida si no n'hi ha cap. */
    public List<Ciudadano> getCiudadanos() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ciudadano", new CiudadanoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ciudadano>();
        }
    }
}
