package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FranjaHorariaDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la franjaHoraria a la base de dades */

    public void addFranjaHoraria(FranjaHoraria franjaHoraria) {
        jdbcTemplate.update("INSERT INTO FranjaHoraria VALUES(?, ?, ?)",franjaHoraria.getIdFranjaHoraria(), franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin());
    }
    /* Esborra la franja horaria de la base de dades */
    public void deleteFranjaHoraria(int franjaHoraria) {
        jdbcTemplate.update("DELETE FROM FranjaHoraria WHERE idFranjaHoraria=?",franjaHoraria);
    }

    /* Actualitza els atributs de la franjaHoraria
       (excepte el idFranjaHoraria, que és la clau primària) */
    public void updateFranjaHoraria(FranjaHoraria franjaHoraria) {
        jdbcTemplate.update("UPDATE ciudadano SET horaInicio=?, horaFin=?, idArea=? where idFranjaHoraria=? ",franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin(),franjaHoraria.getIdFranjaHoraria());
    }
    /* Obté la franjaHorario amb el id donat. Torna null si no existeix. */
    public FranjaHoraria getFranjaHoraria(int idFranjaHoraria) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM FranjaHoraria WHERE idFranjaHoraria = '"+ idFranjaHoraria + "'", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté totes les franjes horaries. Torna una llista buida si no n'hi ha cap. */
    public List<FranjaHoraria> getFranjasHorarias() {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaHoraria", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<FranjaHoraria>();
        }
    }
}
