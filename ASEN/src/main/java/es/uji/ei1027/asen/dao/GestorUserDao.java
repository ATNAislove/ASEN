package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.UserDetails;

public interface GestorUserDao {
    public UserDetails loadUserByUsername(String username, String password);
}
