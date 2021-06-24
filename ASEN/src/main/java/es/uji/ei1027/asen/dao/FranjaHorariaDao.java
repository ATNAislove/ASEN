package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    /* Obté totes les franjes horaries destinades a reservar zones. Torna una llista buida si no n'hi ha cap. */
    public List<FranjaHoraria> getFranjasReserva() {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaHoraria WHERE (horafin-horainicio)<='1:00:00'", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<FranjaHoraria>();
        }
    }
    /* Obté totes les franjes horaries destinades a reservar zones, franjas libres. Torna una llista buida si no n'hi ha cap. */
    public List<FranjaHoraria> getFranjasReserva(String fecha,int idZona) {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaHoraria WHERE (horafin-horainicio)<='1:00:00'" +
                    "EXCEPT select f.* from franjahoraria as f inner join reserva as r on f.idfranjahoraria=r.idfranjahoraria where " +
                    "r.fecha='"+fecha+"' AND r.idZona="+idZona+" AND (horafin-horainicio)<='1:00:00' order by 1", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<FranjaHoraria>();
        }
    }
    /* Obté totes les franjes horaries destinades a reservar zones. Torna una llista buida si no n'hi ha cap. */
    public List<FranjaHoraria> getFranjasReserva(LocalTime horaIni, LocalTime horaFin) {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaHoraria WHERE (horafin-horainicio)<='1:00:00' AND horainicio BETWEEN '"+horaIni+
                    "' AND '"+horaFin+"' AND horafin BETWEEN '"+horaIni+"' AND '"+horaFin+"'", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<FranjaHoraria>();
        }
    }
    /* Obté totes les franjes horaries destinades als serveis. Torna una llista buida si no n'hi ha cap. */
    public List<FranjaHoraria> getFranjasServicio() {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaHoraria WHERE (horafin-horainicio)>'1:00:00'", new FranjaHorariaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<FranjaHoraria>();
        }
    }
}
