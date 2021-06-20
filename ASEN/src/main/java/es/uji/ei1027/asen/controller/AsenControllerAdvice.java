package es.uji.ei1027.asen.controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AsenControllerAdvice {

    @ExceptionHandler(value = AsenApplicationException.class)
    public ModelAndView handleClubException(AsenApplicationException ex){
        ModelAndView mav = new ModelAndView("error/errorException");
        mav.addObject("message", ex.getMessage());
        mav.addObject("errorName", ex.getErrorName());
        mav.addObject("clase",ex.getClase());
        return mav;
    }

}
