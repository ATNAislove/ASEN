package es.uji.ei1027.asen.dao;
import es.uji.ei1027.asen.model.Municipio;
import es.uji.ei1027.asen.model.Vigilancia;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VigilanciaRowMapper implements RowMapper<Vigilancia>{
    public Vigilancia mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vigilancia vigilancia = new Vigilancia();
        vigilancia.setDni(rs.getString("dni"));
        vigilancia.setIdArea(rs.getInt("idArea"));
        return vigilancia;
    }

}
