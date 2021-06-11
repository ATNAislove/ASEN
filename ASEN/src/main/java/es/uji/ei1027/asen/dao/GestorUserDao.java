package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.UserDetails;

public interface GestorUserDao {
    UserDetails loadUserByUsername(String username, String password);
}
