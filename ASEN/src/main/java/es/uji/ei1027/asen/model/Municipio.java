package es.uji.ei1027.asen.model;

public class Municipio {
    private int idMunicipio;
    private String nombreMunicipio;
    private int codigoPostal;
    private String nombreProvincia;
    private int numHabitantes;

    public Municipio() {
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public int getNumHabitantes() {
        return numHabitantes;
    }

    public void setNumHabitantes(int numHabitantes) {
        this.numHabitantes = numHabitantes;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "idMunicipio=" + idMunicipio +
                ", nombreMunicipio='" + nombreMunicipio + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", nombreProvincia='" + nombreProvincia + '\'' +
                ", numHabitantes=" + numHabitantes +
                '}';
    }
}
