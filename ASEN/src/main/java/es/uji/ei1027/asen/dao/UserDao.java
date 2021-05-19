package es.uji.ei1027.asen.dao;

import es.uji.ei1027.asen.model.UserDetails;

import java.util.Collection;

public interface UserDao {
    UserDetails loadUserByUsername(String username, String password);
    Collection<UserDetails> listAllUsers();
}
