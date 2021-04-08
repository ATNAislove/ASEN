package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        jdbcTemplate.update("INSERT INTO Reserva VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",reserva.getIdReserva(),reserva.getFecha(),reserva.getNumeroPersonas()
                ,reserva.getFechaSalida(),reserva.getFechaCreacion(),reserva.getCodigoQR(),reserva.getEstadoReserva()
                ,reserva.getUsuario(),reserva.getIdFranjaHoraria());
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
        jdbcTemplate.update("UPDATE reserva SET fecha=?, numeroPersonas=?, fechaSalida=?, fechaCreacion=?, " +
                        "codigoQR=?, estadoReserva=?, usuario=?, idFranjaHoraria=?" +
                        "WHERE idReserva=?", reserva.getFecha(),reserva.getNumeroPersonas(),
                reserva.getFechaSalida(),reserva.getFechaCreacion(), reserva.getCodigoQR(),
                reserva.getEstadoReserva(),reserva.getUsuario(),reserva.getIdFranjaHoraria(),reserva.getIdReserva());
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


}
