package es.uji.ei1027.asen.model;

public class Disponibilidad {
    private int idFranjaHoraria;
    private int idServicio;

    public int getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public void setIdFranjaHoraria(int idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public String toString() {
        return "Disponibilidad{" +
                "idFranjaHoraria=" + idFranjaHoraria +
                ", idServicio=" + idServicio +
                '}';
    }
}
