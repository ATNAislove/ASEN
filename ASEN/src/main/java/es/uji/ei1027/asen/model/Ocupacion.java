package es.uji.ei1027.asen.model;

public class Ocupacion {
    private int idReserva;
    private int idCharNum;

    public Ocupacion(){
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCharNum() {
        return idCharNum;
    }

    public void setIdCharNum(int idCharNum) {
        this.idCharNum = idCharNum;
    }

    @Override
    public String toString() {
        return "Ocupacion{" +
                "idReserva=" + idReserva +
                ", idZona='" + idCharNum + '\'' +
                '}';
    }
}
