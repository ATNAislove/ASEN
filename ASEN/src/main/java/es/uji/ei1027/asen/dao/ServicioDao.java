package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el servici a la base de dades */

    public void addServicio(Servicio servicio) {
        jdbcTemplate.update("INSERT INTO servicio VALUES(?, ?, ?, ?, ?, ?, ?)",servicio.getIdServicio(), servicio.getFechaInicio(),servicio.getFechaFin(),
                servicio.getTipoServicio(),servicio.getPeriodicidad(),servicio.getTipoTemporada(),servicio.getIdArea());
    }
    /* Esborra el servicio de la base de dades */
    public void deleteServicio(Servicio servicio) {
        jdbcTemplate.update("DELETE FROM servicio WHERE idServicio=?",servicio.getIdServicio());
    }

    public void deleteServicio(int idServicio) {
        jdbcTemplate.update("DELETE FROM servicio WHERE idServicio=?",idServicio);
    }

    /* Actualitza els atributs del servicio
       (excepte id, que és la clau primària) */
    public void updateServicio(Servicio servicio) {
        jdbcTemplate.update("UPDATE servicio SET idServicio=?, fechaInicio=?, fechaFin=?, tipoServicio=?, " +
                        "periodicidad=?, tipoTemporada=?, idArea=?" ,servicio.getIdServicio(), servicio.getFechaInicio(),servicio.getFechaFin(),
        servicio.getTipoServicio(),servicio.getPeriodicidad(),servicio.getTipoTemporada(),servicio.getIdArea());
    }
    /* Obté el servicio amb el id donat. Torna null si no existeix. */
    public Servicio getServicio(int idServicio) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM servicio WHERE idServicio = '"+ idServicio + "'", new ServicioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els servicios. Torna una llista buida si no n'hi ha cap. */
    public List<Servicio> getServicios() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio", new ServicioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Servicio>();
        }
    }
}
