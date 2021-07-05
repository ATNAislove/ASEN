package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class CiudadanoDao implements UserDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el Ciudadano a la base de dades */

    public void addCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("INSERT INTO ciudadano (usuario, pin, fechanacimiento,nombre,apellidos,direccion," +
                        "correoelectronico, municipio, pais,fecharegistro) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                ciudadano.getUsuario(),ciudadano.getPin(),
                ciudadano.getFechaNacimiento(),ciudadano.getNombre(),ciudadano.getApellidos(),
                ciudadano.getDireccion(),ciudadano.getCorreoElectronico(),ciudadano.getMunicipio()
                ,ciudadano.getPais(), LocalDate.now());
    }

    /* Esborra el ciudadano de la base de dades */
    public void deleteCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("DELETE FROM ciudadano WHERE usuario=?",ciudadano.getUsuario());
    }

    public void deleteCiudadano(String usuario) {
        jdbcTemplate.update("DELETE FROM ciudadano WHERE usuario=?",usuario);
    }

    /* Actualitza els atributs del ciudadano
       (excepte el usuario, que és la clau primària) */
    public void updateCiudadano(Ciudadano ciudadano) {
        jdbcTemplate.update("UPDATE ciudadano SET  pin=?, fechanacimiento=?, nombre=?, " +
                        "apellidos=?, direccion=?, correoelectronico=?, municipio=?, pais=?, fecharegistro=?," +
                        "fechabaja=? WHERE usuario=?", ciudadano.getPin(),
                ciudadano.getFechaNacimiento(),ciudadano.getNombre(), ciudadano.getApellidos(),
                ciudadano.getDireccion(),ciudadano.getCorreoElectronico(),ciudadano.getMunicipio(),ciudadano.getPais(),
                ciudadano.getFechaRegistro(),ciudadano.getFechaBaja(), ciudadano.getUsuario());
    }
    /* Obté el ciudadano amb el usuario donat. Torna null si no existeix. */
    public Ciudadano getCiudadano(String usuario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Ciudadano WHERE usuario = '"+ usuario + "'", new CiudadanoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els ciudadanos. Torna una llista buida si no n'hi ha cap. */
    public List<Ciudadano> getCiudadanos() {
        try {
            return jdbcTemplate.query("SELECT * FROM ciudadano", new CiudadanoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ciudadano>();
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        try{
            Ciudadano ciudadano = getCiudadano(username);
            if (ciudadano==null) return null;
            else if(password.equals(ciudadano.getPin())){
                UserDetails user = new UserDetails();
                user.setUsername(username);
                user.setPassword(password);
                user.setRol("Ciudadano");
                return user;
            }
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
        return null;

    }
    @Override
    public Collection<UserDetails> listAllUsers() {
        return null;
    }
}
