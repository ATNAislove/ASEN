package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.model.Servicio;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ServicioValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Servicio.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Servicio servicio = (Servicio) o;
        if(servicio.getFechaInicio().compareTo(servicio.getFechaFin()) > 0){
            errors.rejectValue("fechaFin","n","La fecha fin no puede ser anterior a la fecha inicio");
        }
    }
}
