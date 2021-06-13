package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Disponibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DisponibilidadDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la disponibilitat a la base de dades */
    public void addDisponibilidad(Disponibilidad disponibilidad) {
        jdbcTemplate.update("INSERT INTO disponibilidad VALUES(?, ?)",disponibilidad.getIdFranjaHoraria(), disponibilidad.getIdServicio());
    }
    /* Esborra la disponibilitat de la base de dades */
    public void deleteDisponibilidad(Disponibilidad disponibilidad) {
        jdbcTemplate.update("DELETE FROM disponibilidad WHERE idFranjaHoraria=?",disponibilidad.getIdFranjaHoraria());
    }

    /* Esborra la disponibilitat de la base de dades */
    public void deleteDisponibilidad(int idFranjaHoraria) {
        jdbcTemplate.update("DELETE FROM disponibilidad WHERE idFranjaHoraria=?", idFranjaHoraria);
    }

    /* Actualitza els atributs de la disponibilitat
       (excepte el nom, que és la clau primària) */
    public void updateDisponibilidad(Disponibilidad disponibilidad) {
        jdbcTemplate.update("UPDATE disponibilidad SET idServicio=? WHERE idFranjaHoraria=?", disponibilidad.getIdServicio(), disponibilidad.getIdFranjaHoraria());
    }

    /* Obté la disponibilitat amb el nom donat. Torna null si no existeix. */
    public Disponibilidad getDisponibilidad(int idFranjaHoraria) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM disponibilidad WHERE idFranjaHoraria = '"+ idFranjaHoraria + "'", new DisponibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els disponibilitats. Torna una llista buida si no n'hi ha cap. */
    public List<Disponibilidad> getDisponibilidades() {
        try {
            return jdbcTemplate.query("SELECT * FROM disponibilidad", new DisponibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Disponibilidad>();
        }
    }
    public List<Disponibilidad> getDisponibilidadesServicio(int idServicio){
        try {
            return jdbcTemplate.query("SELECT * FROM disponibilidad WHERE idServicio="+idServicio, new DisponibilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Disponibilidad>();
        }
    }
}

