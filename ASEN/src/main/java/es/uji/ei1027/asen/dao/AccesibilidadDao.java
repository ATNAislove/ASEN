package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Accesibilidad;
import es.uji.ei1027.asen.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccesibilidadDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix l'accesibilidad a la base de dades */
    public void addAccesibilidad(Accesibilidad accesibilidad) {
        jdbcTemplate.update("INSERT INTO Accesibilidad VALUES(?, ?)",accesibilidad.getIdFranjaHoraria(),accesibilidad.getIdArea());
    }
    /* Esborra l'accesibilidad de la base de dades */
    public void deleteAccesibilidad(Accesibilidad accesibilidad) {
        jdbcTemplate.update("DELETE FROM Accesibilidad WHERE idFranjaHoraria=? and idArea=?",accesibilidad.getIdFranjaHoraria(),accesibilidad.getIdArea());
    }

    /* Esborra l'accesibilidad de la base de dades */
    public void deleteAccesibilidad(int idFranjaHoraria,int idArea) {
        jdbcTemplate.update("DELETE FROM municipio WHERE idFranjaHoraria=? and idArea=?", idFranjaHoraria,idArea);
    }

    /* Actualitza els atributs de la accesibilidad
       (excepte el nom, que és la clau primària) */
    /*
    public void updateAccesibilidad(Accesibilidad accesibilidad) {
        jdbcTemplate.update("UPDATE municipio SET idFranjaHoraria=?, idArea=? WHERE idMunicipio=? and idFranjaHoraria=?", );
    }
    */

    /* Obté l'accesibilidats amb el atributs donats. Torna null si no existeix. */
    public Municipio getAccesibilidad(int idFranjaHoraria, int idArea) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Municipio WHERE idMunicipio =" + idFranjaHoraria +"and idArea " +
                    "="+ idArea, new MunicipioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté totes les accesibilitats. Torna una llista buida si no n'hi ha cap. */
    public List<Accesibilidad> getAccesibilidades() {
        try {
            return jdbcTemplate.query("SELECT * FROM Accesibilidad", new AccesibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Accesibilidad>();
        }
    }
}
