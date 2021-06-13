package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Ocupacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OcupacionDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la ocupacio a la base de dades */

    public void addOcupacio(Ocupacion ocupacion) {
        jdbcTemplate.update("INSERT INTO Ocupacion VALUES(?, ?)",ocupacion.getIdReserva(),ocupacion.getIdZona());
    }
    /* Esborra la ocupacio de la base de dades */
    public void deleteOcupacio(int idReserva) {
        jdbcTemplate.update("DELETE FROM ocupacion WHERE idReserva=?",idReserva);
    }

    public void deleteOcupacio(Ocupacion ocupacion) {
        jdbcTemplate.update("DELETE FROM ocupacion WHERE idReserva=?, idZona=?",ocupacion.getIdReserva(),ocupacion.getIdZona());
    }

    //No hay que actualizar la ocupacion.

    /* Obté la ocupacio amb els dos atributs donats. Torna null si no existeix. */

    public Ocupacion getOcupacio(int idReserva,String idCharNum) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Prova WHERE idReserva =? and idZona=? ",
                    new OcupacionRowMapper(),idReserva,idCharNum);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté totes les ocupacion. Torna una llista buida si no n'hi ha cap. */
    public List<Ocupacion> getOcupacions() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ocupacion", new OcupacionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ocupacion>();
        }
    }
}
