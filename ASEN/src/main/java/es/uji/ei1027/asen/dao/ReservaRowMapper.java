package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ReservaRowMapper implements RowMapper<Reserva> {
    public Reserva mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("idReserva"));
        reserva.setFecha(rs.getObject("fecha",LocalDate.class));
        reserva.setNumeroPersonas(rs.getInt("numeroPersonas"));
        reserva.setHoraSalida(rs.getObject("horaSalida", LocalTime.class));
        reserva.setFechaCreacion(rs.getObject("fechaCreacion",LocalDate.class));
        reserva.setCodigoQR(rs.getString("codigoQR"));
        reserva.setEstadoReserva(rs.getString("estadoReserva"));
        reserva.setUsuario(rs.getString("usuario"));
        reserva.setIdFranjaHoraria(rs.getInt("idFranjaHoraria"));
        /*Array array = rs.getArray("zonas");
        List lista = new ArrayList<>();
        Collections.addAll(lista, array);
        reserva.setZonas(lista);*/
        return reserva;
    }
}
