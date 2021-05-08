package es.uji.ei1027.asen.model;

public class Accesibilidad {
    private int idFranjaHoraria;
    private int idArea;

    public Accesibilidad(){
    }

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    @Override
    public String toString() {
        return "Accesibilidad{" +
                "idFranjaHoraria=" + idFranjaHoraria +
                ", idArea=" + idArea +
                '}';
    }

}
