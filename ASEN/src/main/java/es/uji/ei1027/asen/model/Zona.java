package es.uji.ei1027.asen.model;

public class Zona {
    private String idCharNumero;
    private String nombreZona;
    private int aforoMaximo;
    private String tipoTerreno;

    public Zona() {
    }

    public String getIdCharNumero() {
        return idCharNumero;
    }

    public void setIdCharNumero(String idCharNumero) {
        this.idCharNumero = idCharNumero;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public String getTipoTerreno() {
        return tipoTerreno;
    }

    public void setTipoTerreno(String tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    @Override
    public String toString() {
        return "Zona{" +
                "idCharNumero='" + idCharNumero + '\'' +
                ", nombreZona='" + nombreZona + '\'' +
                ", aforoMaximo=" + aforoMaximo +
                ", tipoTerreno='" + tipoTerreno + '\'' +
                '}';
    }
}
