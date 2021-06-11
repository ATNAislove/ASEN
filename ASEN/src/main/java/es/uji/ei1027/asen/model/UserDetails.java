package es.uji.ei1027.asen.model;

public class UserDetails {
    String username;
    String password;
    String rol;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol(){
        return rol;
    }
    public void setRol(String rol){
        this.rol = rol;
    }
    @Override
    public String toString() {
        return username;
    }
}

