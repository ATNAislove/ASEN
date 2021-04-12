package es.uji.ei1027.asen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class GestorMunicipal {
    private String dni;
    private String nombre;
    private String apellidos;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaNacimiento;
    private String correoElectronico;
    private String direccion;
    private String municipioNacimiento;
    private String pais;
    private long telefono;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaRegistro;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaBaja;
    private int idMunicipio;

    public GestorMunicipal() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipioNacimiento() {
        return municipioNacimiento;
    }

    public void setMunicipioNacimiento(String municipioNacimiento) {
        this.municipioNacimiento = municipioNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
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

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    @Override
    public String toString() {
        return "GestorMunicipal{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccion='" + direccion + '\'' +
                ", municipioNacimiento='" + municipioNacimiento + '\'' +
                ", pais='" + pais + '\'' +
                ", telefono=" + telefono +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaBaja=" + fechaBaja +
                ", idMunicipio=" + idMunicipio +
                '}';
    }
}