package es.uji.ei1027.asen.model;

public class ReservaArea {
    private String TipoArea;
    private String nombreArea;
    private Reserva reserva;
    public ReservaArea(){

    }

    public String getTipoArea() {
        return TipoArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setTipoArea(String tipoArea) {
        TipoArea = tipoArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "ReservaArea{" +
                "TipoArea='" + TipoArea + '\'' +
                ", nombreArea='" + nombreArea + '\'' +
                ", reserva=" + reserva.getIdReserva() +
                '}';
    }
}
