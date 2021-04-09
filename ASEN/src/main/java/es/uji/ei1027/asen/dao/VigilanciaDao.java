package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Vigilancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VigilanciaDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el vigilancia a la base de dades */
    public void addVigilancia(Vigilancia vigilancia) {
        jdbcTemplate.update("INSERT INTO Vigilancia VALUES(?, ?)", vigilancia.getDni(), vigilancia.getIdArea());
    }
    /* Esborra el vigilancia de la base de dades */
    public void deleteVigilancia(Vigilancia vigilancia) {
        jdbcTemplate.update("DELETE FROM vigilancia WHERE dni=?",vigilancia.getDni());
    }

    /* Esborra el vigilancia de la base de dades */
    public void deleteVigilancia(String dni) {
        jdbcTemplate.update("DELETE FROM vigilancia WHERE dni=?", dni);
    }

    /* Actualitza els atributs del vigilancia
       (excepte el nom, que és la clau primària) */
    public void updateVigilancia(Vigilancia vigilancia) {
        jdbcTemplate.update("UPDATE vigilancia SET dni=?, idArea=?", vigilancia.getDni(),vigilancia.getIdArea());
    }

    /* Obté el vigilancia amb el nom donat. Torna null si no existeix. */
    public Vigilancia getVigilancia(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Vigilancia WHERE dni = '"+ dni + "'", new VigilanciaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els vigilancia. Torna una llista buida si no n'hi ha cap. */
    public List<Vigilancia> getVigilancias() {
        try {
            return jdbcTemplate.query("SELECT * FROM Vigilancia", new VigilanciaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Vigilancia>();
        }
    }
}
