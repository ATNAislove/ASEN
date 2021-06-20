package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservaDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la Reserva a la base de dades */

    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva (fecha, numeroPersonas, horaSalida, fechaCreacion, codigoQr, estadoReserva, usuario,idFranjaHoraria,idZona) VALUES( ?, ?, ?, ?, ?, ?, ?, ?,?)"
                ,reserva.getFecha(),reserva.getNumeroPersonas()
                ,reserva.getHoraSalida(), LocalDate.now(),reserva.getCodigoQR(),"pendiente"
                ,reserva.getUsuario(),reserva.getIdFranjaHoraria(),reserva.getIdZona());
    }

    /*Cancelar una reserva*/
    public void cancelarReserva(int idReserva){
        jdbcTemplate.update("UPDATE reserva SET estadoReserva=? WHERE idReserva=?","cancelado",idReserva);

    }

    /* Esborra la reserva de la base de dades */
    public void deleteReserva(Reserva reserva) {
        jdbcTemplate.update("DELETE FROM Reserva WHERE idReserva=?",reserva.getIdReserva());
    }
    public void deleteReserva(int idReserva) {
        jdbcTemplate.update("DELETE FROM reserva WHERE idReserva=?",idReserva);
    }

    /* Actualitza els atributs de la reserva
       (excepte el idReserva, que és la clau primària) */
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET fecha=?, numeroPersonas=?, horaSalida=?, " +
                          "idFranjaHoraria=?, codigoqr=? WHERE idReserva=?",
                reserva.getFecha(),reserva.getNumeroPersonas(), reserva.getHoraSalida(),
                reserva.getIdFranjaHoraria(),reserva.getCodigoQR(),reserva.getIdReserva());
    }
    /* Actualitza els atributs de la reserva si ets un gestor
       (excepte el idReserva, que és la clau primària) */
    public void updateReservaEstado(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET fecha=?, numeroPersonas=?, horaSalida=?, estadoReserva=?, " +
                        "idFranjaHoraria=?, codigoqr=? WHERE idReserva=?",
                reserva.getFecha(),reserva.getNumeroPersonas(), reserva.getHoraSalida(),reserva.getEstadoReserva(),
                reserva.getIdFranjaHoraria(),reserva.getCodigoQR(),reserva.getIdReserva());
    }

    public void updateHoraSalidaReserva(int idReserva) {
        jdbcTemplate.update("UPDATE reserva SET  horaSalida=?, estadoReserva=?" +
                        "WHERE idReserva=?",
                LocalTime.now(),"usado",idReserva);
    }
    /* Obté la reserva amb el idReserva donat. Torna null si no existeix. */
    public Reserva getReserva(int idReserva) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Reserva WHERE idReserva = '"+ idReserva + "'", new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté totes les reserves. Torna una llista buida si no n'hi ha cap. */
    public List<Reserva> getReservas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva", new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    public List<Reserva> getReservasUsuario(String usuario) {
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva where usuario= '"+usuario +"' AND estadoReserva NOT IN ('cancelado')", new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    public List<Reserva> getReservasConcretas(Reserva reserva,String usuario){
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva where usuario= '"+usuario+"' AND estadoReserva NOT IN ('cancelado')" +
                            " AND fecha='"+reserva.getFecha()+"' AND idfranjahoraria="+reserva.getIdFranjaHoraria()+
                            " AND fechacreacion='"+reserva.getFechaCreacion()+"' AND idZona="+reserva.getIdZona()
                    , new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    public List<Reserva> existeReserva(Reserva reserva){
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva where estadoReserva NOT IN ('cancelado')" +
                            " AND fecha='"+reserva.getFecha().toString()+"' AND idfranjahoraria="+reserva.getIdFranjaHoraria()+
                            "  AND idZona="+reserva.getIdZona()
                    , new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }

    //Obteener las reservas en una fecha determinada
    public List<Reserva> getReservasByFecha(String fecha, int idArea){
        try{
            return jdbcTemplate.query("SELECT r.* FROM reserva AS r" +
                    "INNER JOIN zona as z ON r.idZona=z.idZona" +
                    "WHERE r.fecha=" + fecha+ "AND z.idArea='"+idArea+"'", new ReservaRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Reserva>();
        }
    }

}
