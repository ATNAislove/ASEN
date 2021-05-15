package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Servicio;
import es.uji.ei1027.asen.model.TipoServicio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TipoServicioRowMapper  implements RowMapper<TipoServicio>{
    @Override
    public TipoServicio mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setIdTipoServicio(rs.getInt("idTipoServicio"));
        tipoServicio.setNombre(rs.getString("nombre"));
        return tipoServicio;
    }
}
