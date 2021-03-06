package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.GestorMunicipal;
import es.uji.ei1027.asen.model.UserDetails;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class GestorMunicipalDao implements UserDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el gestorMunicipal a la base de dades */
    public void addGestorMunicipal(GestorMunicipal gestorMunicipal) {
        jdbcTemplate.update("INSERT INTO gestorMunicipal VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)",gestorMunicipal.getDni(),
                gestorMunicipal.getNombre(),gestorMunicipal.getApellidos(), gestorMunicipal.getFechaNacimiento(),gestorMunicipal.getCorreoElectronico(),
                gestorMunicipal.getUsuario(),gestorMunicipal.getContrasenya(),gestorMunicipal.getDireccion(),gestorMunicipal.getMunicipioNacimiento(),gestorMunicipal.getPais(),gestorMunicipal.getTelefono(),
                gestorMunicipal.getFechaRegistro(),gestorMunicipal.getFechaBaja(),gestorMunicipal.getIdMunicipio());
    }
    /* Esborra el gestorMunicipal de la base de dades */
    public void deleteGestorMunicipal(GestorMunicipal gestorMunicipal) {
        jdbcTemplate.update("DELETE FROM gestorMunicipal WHERE dni=?",gestorMunicipal.getDni());
    }

    /* Esborra el gestorMunicipal de la base de dades */
    public void deleteGestorMunicipal(String dni) {
        jdbcTemplate.update("DELETE FROM gestorMunicipal WHERE dni=?", dni);
    }

    /* Actualitza els atributs del gestorMunicipal
       (excepte el nom, que és la clau primària) */
    public void updateGestorMunicipal(GestorMunicipal gestorMunicipal) {
        jdbcTemplate.update("UPDATE gestorMunicipal SET nombre=?, apellidos=?, fechaNacimiento=?, correoElectronico=?,usuario=?,contrasenya=?,direccion=?,municipioNacimineto=?," +
                        "pais=?,telefono=?,fechaRegistro=?,fechaBaja=?,idMunicipio=? WHERE dni=?",
                gestorMunicipal.getNombre(),gestorMunicipal.getApellidos(),gestorMunicipal.getFechaNacimiento(),gestorMunicipal.getCorreoElectronico(),
                gestorMunicipal.getUsuario(),gestorMunicipal.getContrasenya(),gestorMunicipal.getDireccion(),gestorMunicipal.getMunicipioNacimiento(),gestorMunicipal.getPais(),gestorMunicipal.getTelefono(),
                gestorMunicipal.getFechaRegistro(),gestorMunicipal.getFechaBaja(),gestorMunicipal.getIdMunicipio(),gestorMunicipal.getDni());
    }

    /* Obté el gestorMunicipal amb el dni donat. Torna null si no existeix. */
    public GestorMunicipal getGestorMunicipal(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM gestorMunicipal WHERE dni = '"+ dni + "'", new GestorMunicipalRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    //Obté el gestorMunicipal amb el nom donat. Torna null si no existeix
    public GestorMunicipal getGestorMunicipalUser(String usuario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM gestorMunicipal WHERE usuario = '"+ usuario + "'", new GestorMunicipalRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els gestorMunicipals. Torna una llista buida si no n'hi ha cap. */
    public List<GestorMunicipal> getGestoresMunicipales() {
        try {
            return jdbcTemplate.query("SELECT * FROM gestorMunicipal", new GestorMunicipalRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<GestorMunicipal>();
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        try {
            GestorMunicipal gestorMunicipal = getGestorMunicipalUser(username);
            if (gestorMunicipal == null) return null;
            else if (password.equals(gestorMunicipal.getContrasenya())) {
                UserDetails user = new UserDetails();
                user.setUsername(username);
                user.setPassword(password);
                user.setRol("GestorMunicipal");
                return user;
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return null;
    }

}
