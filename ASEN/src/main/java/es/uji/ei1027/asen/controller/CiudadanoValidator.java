package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.model.Ciudadano;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class CiudadanoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Ciudadano.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Ciudadano ciudadano = (Ciudadano) o;
        if(ciudadano.getFechaNacimiento().compareTo(LocalDate.now())> 0){
            errors.rejectValue("fechaNacimiento", "obligatorio",
                    "Debes introducir una fecha anterior a la actual");
        }
        if(ciudadano.getFechaBaja()!=null && ciudadano.getFechaBaja().compareTo(LocalDate.now())<0){
            errors.rejectValue("fechaBaja", "No obligatorio",
                    "Debes introducir una fecha posterior a la actual");
        }
    }
}
