package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZonaDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el municipio a la base de dades */
    public void addZona(Zona zona) {
        jdbcTemplate.update("INSERT INTO Zona VALUES(?, ?, ?, ?)", zona.getIdCharNumero(), zona.getNombreZona(),
                zona.getAforoMaximo(), zona.getTipoTerreno());
    }

    /* Esborra el zona de la base de dades */
    public void deleteZona(Zona zona) {
        jdbcTemplate.update("DELETE FROM zona WHERE idCharNumero=?", zona.getIdCharNumero());
    }

    /* Esborra el zona de la base de dades */
    public void deleteZona(String idCharNumero) {
        jdbcTemplate.update("DELETE FROM zona WHERE idCharNumero=?", idCharNumero);
    }

    /* Actualitza els atributs del zona
       (excepte el nom, que és la clau primària) */
    public void updateZona(Zona zona) {
        jdbcTemplate.update("UPDATE zona SET nombreZona=?, aforoMaximo=?, tipoTerreno=? WHERE idCharNumero=?",
                zona.getNombreZona(), zona.getAforoMaximo(), zona.getTipoTerreno(), zona.getIdCharNumero());
    }

    /* Obté el zona amb el nom donat. Torna null si no existeix. */
    public Zona getZona(String idCharNumero) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Zona WHERE idCharNumero = '" + idCharNumero + "'", new ZonaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els zonas. Torna una llista buida si no n'hi ha cap. */
    public List<Zona> getZonas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Zona", new ZonaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zona>();
        }
    }
}