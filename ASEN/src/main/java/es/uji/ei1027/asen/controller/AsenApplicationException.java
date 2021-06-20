package es.uji.ei1027.asen.controller;

public class AsenApplicationException extends RuntimeException {

    String message;     // Missatge per mostrar a la vista
    String errorName;     // Identificador de l’error
    String clase;           //tipus de alerta


    public AsenApplicationException(String message, String errorName,String clase)
    {
        this.message=message;
        this.errorName=errorName;
        this.clase=clase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }
}
