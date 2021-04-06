package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.AreaNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AreaNaturalDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el areaNatural a la base de dades */
    public void addAreaNatural(AreaNatural areaNatural) {
        jdbcTemplate.update("INSERT INTO areaNatural VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?)",areaNatural.getIdArea(),
                areaNatural.getNombreArea(),areaNatural.getDescripcion(),areaNatural.getImagen(),areaNatural.getTipoTerreno(),
                areaNatural.getTipoServicio(),areaNatural.getInstalacion(),areaNatural.getTipoArea(),areaNatural.getTipoAcceso());
    }
    /* Esborra el areaNatural de la base de dades */
    public void deleteAreaNatural(AreaNatural areaNatural) {
        jdbcTemplate.update("DELETE FROM areaNatural WHERE idArea=?",areaNatural.getIdArea());
    }

    /* Esborra el areaNatural de la base de dades */
    public void deleteAreaNatural(int idArea) {
        jdbcTemplate.update("DELETE FROM areaNatural WHERE idArea=?", idArea);
    }

    /* Actualitza els atributs del areaNatural
       (excepte el id, que és la clau primària) */
    public void updateAreaNatural(AreaNatural areaNatural) {
        jdbcTemplate.update("UPDATE areaNatural SET nombreArea=?, descripcion=?, imagen=?, tipoTerreno=?,tipoServicio=?,instalacion=?," +
                        "tipoArea=?,tipoAcceso=? WHERE idArea=?",
                areaNatural.getNombreArea(),areaNatural.getDescripcion(),areaNatural.getImagen(),areaNatural.getTipoTerreno(),
                areaNatural.getTipoServicio(),areaNatural.getInstalacion(),areaNatural.getTipoArea(),areaNatural.getTipoAcceso(),areaNatural.getIdArea());
    }

    /* Obté el areaNatural amb el nom donat. Torna null si no existeix. */
    public AreaNatural getAreaNatural(int idArea) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM areaNatural WHERE idArea = '"+ idArea + "'", new AreaNaturalRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els areaNaturals. Torna una llista buida si no n'hi ha cap. */
    public List<AreaNatural> getAreasNaturales() {
        try {
            return jdbcTemplate.query("SELECT * FROM areaNatural", new AreaNaturalRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<AreaNatural>();
        }
    }
}
