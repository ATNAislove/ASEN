package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Accesibilidad;
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
        jdbcTemplate.update("INSERT INTO Accesibilidad VALUES(?, ?, ?, ?)",
                accesibilidad.getIdArea(),accesibilidad.getIdFranjaHoraria(),accesibilidad.getFechaInicio(),accesibilidad.getFechaFin());
    }
    /* Esborra l'accesibilidad de la base de dades */
    public void deleteAccesibilidad(Accesibilidad accesibilidad) {
        jdbcTemplate.update("DELETE FROM Accesibilidad WHERE idFranjaHoraria=? and idArea=?",accesibilidad.getIdFranjaHoraria(),accesibilidad.getIdArea());
    }

    /* Esborra l'accesibilidad de la base de dades */
    public void deleteAccesibilidad(int idFranjaHoraria, int idArea, String fechaInicio) {
        jdbcTemplate.update("DELETE FROM accesibilidad WHERE idFranjaHoraria=? and idArea=? and fechaInicio='"+fechaInicio+"'", idFranjaHoraria, idArea);
    }

    /* Actualitza la data final de accesibilidad, la resta no, son claus primaries */
    public void updateAccesibilidad(Accesibilidad accesibilidad) {
        jdbcTemplate.update("UPDATE accesibilidad SET fechaFin=? WHERE idArea=? and idFranjaHoraria=? and fechaInicio=?",
                accesibilidad.getFechaFin(),accesibilidad.getIdArea(),accesibilidad.getIdFranjaHoraria(),accesibilidad.getFechaInicio() );
    }


    /* Obté l'accesibilidats amb el atributs donats. Torna null si no existeix. */
    public Accesibilidad getAccesibilidad(int idFranjaHoraria, int idArea, String fechaInicio) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM accesibilidad WHERE idfranjahoraria=" + idFranjaHoraria +"and idArea="+
                    idArea+" and fechaInicio='"+fechaInicio+"'", new AccesibilidadRowMapper());
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
    /* Obté totes les accesibilitats d'un area. Torna una llista buida si no n'hi ha cap. */
    public List<Accesibilidad> getAccesibilidadesArea(int idArea) {
        try {
            return jdbcTemplate.query("SELECT * FROM Accesibilidad where idArea="+idArea, new AccesibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Accesibilidad>();
        }
    }
    /* Obté totes les accesibilitats d'un area para una fecha concreta. Torna una llista buida si no n'hi ha cap. */
    public List<Accesibilidad> getAccesibilidadesArea(int idArea, String fecha) {
        try {
            return jdbcTemplate.query("SELECT * FROM Accesibilidad where idArea="+idArea+" and '"+fecha+"' >= fechainicio and ('"+fecha+"' <= fechafin or fechafin IS null)", new AccesibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Accesibilidad>();
        }
    }
}
