package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.model.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
