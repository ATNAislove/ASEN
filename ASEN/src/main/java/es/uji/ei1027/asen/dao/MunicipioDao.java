package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MunicipioDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el municipio a la base de dades */
    public void addMunicipio(Municipio municipio) {
        jdbcTemplate.update("INSERT INTO Municipio VALUES(?, ?, ?, ?, ?)",municipio.getIdMunicipio(), municipio.getNombreMunicipio(),municipio.getCodigoPostal(),
                municipio.getNombreProvincia(),municipio.getPoblacion());
    }
    /* Esborra el municipio de la base de dades */
    public void deleteMunicipio(Municipio municipio) {
        jdbcTemplate.update("DELETE FROM municipio WHERE idMunicipio=?",municipio.getIdMunicipio());
    }

    /* Esborra el municipio de la base de dades */
    public void deleteMunicipio(int idMunicipio) {
        jdbcTemplate.update("DELETE FROM municipio WHERE idMunicipio=?", idMunicipio);
    }

    /* Actualitza els atributs del municipio
       (excepte el nom, que és la clau primària) */
    public void updateMunicipio(Municipio municipio) {
        jdbcTemplate.update("UPDATE municipio SET nombreMunicipio=?, codigoPostal=?, nombreProvincia=?, poblacion=? WHERE idMunicipio=?", municipio.getNombreMunicipio(),municipio.getCodigoPostal(),
                municipio.getNombreProvincia(),municipio.getPoblacion(), municipio.getIdMunicipio());
    }

    /* Obté el municipio amb el nom donat. Torna null si no existeix. */
    public Municipio getMunicipio(int idMunicipio) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Municipio WHERE idMunicipio = '"+ idMunicipio + "'", new MunicipioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els municipios. Torna una llista buida si no n'hi ha cap. */
    public List<Municipio> getMunicipios() {
        try {
            return jdbcTemplate.query("SELECT * FROM Municipio", new MunicipioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Municipio>();
        }
    }
}
