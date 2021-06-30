package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
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
        jdbcTemplate.update("INSERT INTO Zona( idcharnum, nombrezona, aforomaximo, tipoterreno, idarea) VALUES (?,?,?,?,?)", zona.getIdCharNumero(), zona.getNombreZona(),
                zona.getAforoMaximo(), zona.getTipoTerreno(),zona.getIdArea());
    }

    /* Esborra el zona de la base de dades */
    public void deleteZona(Zona zona) {
        jdbcTemplate.update("DELETE FROM zona WHERE idzona=?", zona.getIdZona());
    }

    /* Esborra el zona de la base de dades */
    public void deleteZona(int idZona) {
        jdbcTemplate.update("DELETE FROM zona WHERE idZona=?", idZona);
    }

    /* Actualitza els atributs del zona
       (excepte el nom, que és la clau primària) */
    public void updateZona(Zona zona) {
        jdbcTemplate.update("UPDATE zona SET  nombreZona=?, aforoMaximo=?, tipoTerreno=? WHERE idZona=?",
                 zona.getNombreZona(), zona.getAforoMaximo(), zona.getTipoTerreno(),zona.getIdZona());
    }

    /* Obté el zona amb el nom donat. Torna null si no existeix. */
    public Zona getZona(int idZona) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Zona WHERE idZona = '" + idZona + "'", new ZonaRowMapper());
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
    //obtiene las zonas de un area
    public List<Zona> getZonas(int idArea){
        try {
            return jdbcTemplate.query("SELECT * FROM Zona where idArea="+idArea, new ZonaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zona>();
        }
    }
    /* Obté tots els zonas lliures un dia y a una franja horaria. Torna una llista buida si no n'hi ha cap. */
    public List<Zona> getZonas(String fecha, int idFranjaHoraria, int idArea) {
        try {
            return jdbcTemplate.query("select * from zona where idArea="+idArea+
                    " except select z.* from zona as z inner join ocupacion as o on z.idzona=o.idzona " +
                    "inner join reserva as r  ON r.idReserva=o.idReserva " +
                    "where r.fecha='"+fecha+"' and r.idFranjaHoraria="+idFranjaHoraria+" order by 2", new ZonaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zona>();
        }
    }



    /* Obté totes les zones en un area. Torna una llista buida si no n'hi ha cap. */
    public List<Zona> getZonasByArea(int idArea) {
        try {
            return jdbcTemplate.query("SELECT * FROM Zona WHERE idArea="+idArea+" order by 2", new ZonaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zona>();
        }
    }
    //obtener el numero de personas de un area
    public int getMaximoPersonasArea(int idArea){
        try{
            return jdbcTemplate.queryForObject("SELECT sum(aforomaximo) from zona where idArea="+idArea,Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
    //obtener el numero de personas de una zona
    public int getMaximoPersonas(int idZona){
        try{
            return jdbcTemplate.queryForObject("SELECT aforomaximo from zona where idZona="+idZona,Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    //Obtener el numero de zonas de un area
    public int numZonas(int idArea){
        try {
            return jdbcTemplate.queryForObject("SELECT count(*) FROM Zona WHERE idArea='"+idArea+"'", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
    //Obteener las zonas ocupadas en una fecha determinada
    public List<Zona> getZonasOcupadasByFecha(String fecha, int idArea){
        try{
            return jdbcTemplate.query("SELECT z.* FROM zonas AS z" +
                    "INNER JOIN ocupacion as c ON c.idZona=z.idZona" +
                    "INNER JOIN reserva as r ON r.idReserva=c.idReserva" +
                    "WHERE r.fecha=" + fecha+ "AND z.idArea='"+idArea+"'", new ZonaRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Zona>();
        }
    }

}