package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.TipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoServicioDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void addTipoServicio(TipoServicio tipoServicio) {
        jdbcTemplate.update("INSERT INTO tipoServicio VALUES(?, ?)",tipoServicio.getIdTipoServicio(), tipoServicio.getNombre());
    }
    /* Esborra el tipoServicio de la base de dades */
    public void deleteTipoServicio(TipoServicio tipoServicio) {
        jdbcTemplate.update("DELETE FROM tipoServicio WHERE idTipoServicio=?",tipoServicio.getIdTipoServicio());
    }

    public void deleteTipoServicio(int idTipoServicio) {
        jdbcTemplate.update("DELETE FROM tipoServicio WHERE idTipoServicio=?",idTipoServicio);
    }

    /* Actualitza els atributs del tipoServicio
       (excepte dni, que és la clau primària) */
    public void updateTipoServicio(TipoServicio tipoServicio) {
        jdbcTemplate.update("UPDATE tipoServicio SET idTipoServicio=?, nombre=?" , tipoServicio.getIdTipoServicio(),tipoServicio.getNombre());
    }
    /* Obté el tipoServicio amb el id donat. Torna null si no existeix. */
    public TipoServicio getTipoServicio(int idTipoServicio) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tipoServicio WHERE idTipoServicio = "+ idTipoServicio , new TipoServicioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els tipoServicio. Torna una llista buida si no n'hi ha cap. */
    public List<TipoServicio> getTipoServicios() {
        try {
            return jdbcTemplate.query("SELECT * FROM tipoServicio", new TipoServicioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<TipoServicio>();
        }
    }
}
