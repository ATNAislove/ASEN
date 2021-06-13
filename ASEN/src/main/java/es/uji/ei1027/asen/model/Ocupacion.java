package es.uji.ei1027.asen.model;

public class Ocupacion {
    private int idReserva;
    private int idZona;

    public Ocupacion(){
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    @Override
    public String toString() {
        return "Ocupacion{" +
                "idReserva=" + idReserva +
                ", idZona='" + idZona + '\'' +
                '}';
    }
}
