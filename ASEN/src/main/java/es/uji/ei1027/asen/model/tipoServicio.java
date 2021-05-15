package es.uji.ei1027.asen.model;

public class TipoServicio {
    private int idTipoServicio;
    private String nombre;

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "tipoServicio{" +
                "idTipoServicio=" + idTipoServicio +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
