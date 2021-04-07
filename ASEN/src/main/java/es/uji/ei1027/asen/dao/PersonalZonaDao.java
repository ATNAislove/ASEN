package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.FranjaHoraria;
import es.uji.ei1027.asen.model.PersonalZona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonalZonaDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el personal a la base de dades */

    public void addPersonalZona(PersonalZona personalZona) {
        jdbcTemplate.update("INSERT INTO personalZona VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",personalZona.getDni(), personalZona.getNombre(),personalZona.getApellidos(), personalZona.getFechaNacimiento(),
                personalZona.getCorreoElectronico(),personalZona.getDireccion(),personalZona.getMunicipio(),personalZona.getPais(), personalZona.getTelefono());
    }
    /* Esborra el personal de la base de dades */
    public void deletePersonalZona(PersonalZona personalZona) {
        jdbcTemplate.update("DELETE FROM personalZona WHERE dni=?",personalZona.getDni());
    }

    public void deletePersonalZona(String dni) {
        jdbcTemplate.update("DELETE FROM personalZona WHERE dni=?",dni);
    }

    /* Actualitza els atributs del personal
       (excepte dni, que és la clau primària) */
    public void updatePersonalZona(PersonalZona personalZona) {
        jdbcTemplate.update("UPDATE personalZona SET dni=?, nombre=?, apellidos=?, fechaNacimiento=?, " +
                        "correoElectronico=?, direccion=?, municipio=?, pais=?, telefono=?" ,personalZona.getDni(), personalZona.getNombre(),personalZona.getApellidos(), personalZona.getFechaNacimiento(),
                personalZona.getCorreoElectronico(),personalZona.getDireccion(),personalZona.getMunicipio(),personalZona.getPais(), personalZona.getTelefono());
    }
    /* Obté el personal amb el id donat. Torna null si no existeix. */
    public PersonalZona getPersonalZona(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM personalZona WHERE dni = '"+ dni + "'", new PersonalZonaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els personal. Torna una llista buida si no n'hi ha cap. */
    public List<PersonalZona> getPersonalZonas() {
        try {
            return jdbcTemplate.query("SELECT * FROM personalZona", new PersonalZonaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<PersonalZona>();
        }
    }


}
