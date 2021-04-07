package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Ocupacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class OcupacionDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la ocupacio a la base de dades */

    public void addOcupacio(Ocupacion ocupacion) {
        jdbcTemplate.update("INSERT INTO Ocupacion VALUES(?, ?)",ocupacion.getIdReserva(),ocupacion.getIdCharNum());
    }
    /* Esborra la ocupacio de la base de dades */
    public void deleteOcupacio(int idReserva,String idCharNum) {
        jdbcTemplate.update("DELETE FROM ciudadano WHERE idReserva=? AND idCharNum=?",idReserva,idCharNum);
    }

    //No hay que actualizar la ocupacion.

    /* Obté la ocupacio amb els dos atributs donats. Torna null si no existeix. */
    public Ocupacion getOcupacio(int idReserva,String idCharNum) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Prova WHERE idReserva =? and idCharNum=? ",
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
