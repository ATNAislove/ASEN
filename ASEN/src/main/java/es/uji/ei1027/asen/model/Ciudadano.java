package es.uji.ei1027.asen.model;

import java.time.LocalDate;

public class Ciudadano {

    private String usuario;
    private String codigoCiudadano;
    private String pin;
    private LocalDate fechaNacimiento;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String correoElectronico;
    private String municipio;
    private String pais;
    private LocalDate fechaRegistro;
    private LocalDate fechaBaja;

    public Ciudadano(){
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigoCiudadano() {
        return codigoCiudadano;
    }

    public void setCodigoCiudadano(String codigoCiudadano) {
        this.codigoCiudadano = codigoCiudadano;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    @Override
    public String toString() {
        return "Ciudadano{" +
                "usuario=" + usuario +
                ", codigoCiudadano='" + codigoCiudadano + '\'' +
                ", pin=" + pin +
                ", fechaNacimiento ='" + fechaNacimiento + '\'' +
                ", nombre=" + nombre+
                ", apellidos=" + apellidos +
                ", direccion=" + direccion +
                ", correoElectronico= " +correoElectronico +
                ", municipio= " +correoElectronico+
                ", pais=" +pais+
                ", fechaRegistro" +fechaRegistro+
                ", fechaBaja" +fechaBaja+
                '}';
    }
}

