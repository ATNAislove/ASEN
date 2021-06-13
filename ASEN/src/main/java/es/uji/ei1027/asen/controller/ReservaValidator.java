package es.uji.ei1027.asen.controller;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Reserva.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Reserva reserva = (Reserva) obj;
        // Validación para una fecha válida
        if (reserva.getFecha().compareTo(LocalDate.now()) < 0) {
            errors.rejectValue("fecha", "obligatorio",
                    "Debes introducir una fecha posterior a la actual");
        }
    }
}
