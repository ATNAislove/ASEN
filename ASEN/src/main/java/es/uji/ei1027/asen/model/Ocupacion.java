package es.uji.ei1027.asen.model;

public class Ocupacion {
    private int idReserva;
    private String idCharNum;

    public Ocupacion(){
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdCharNum() {
        return idCharNum;
    }

    public void setIdCharNum(String idCharNum) {
        this.idCharNum = idCharNum;
    }

    @Override
    public String toString() {
        return "Ocupacion{" +
                "idReserva=" + idReserva +
                ", idCharNum='" + idCharNum + '\'' +
                '}';
    }
}
