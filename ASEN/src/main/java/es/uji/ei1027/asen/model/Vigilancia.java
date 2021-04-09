package es.uji.ei1027.asen.model;

public class Vigilancia {
    private String dni;
    private int idArea;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    @Override
    public String toString() {
        return "Vigilancia{" +
                "dni='" + dni + '\'' +
                ", idArea=" + idArea +
                '}';
    }
}
