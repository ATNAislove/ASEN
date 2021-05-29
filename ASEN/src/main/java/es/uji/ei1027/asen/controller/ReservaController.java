package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaDao reservaDao;

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listReservas(Model model) {
        model.addAttribute("reservas", reservaDao.getReservas());
        return "reserva/list";
    }
    @RequestMapping(value = "/add")
    public String addReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult) {
        ReservaValidator reservaValidator = new ReservaValidator();
        reservaValidator.validate(reserva,bindingResult);
        if (bindingResult.hasErrors())
            return "reserva/add";
        try{
            reservaDao.addReserva(reserva);
        } catch(DuplicateKeyException e){
            throw new AsenApplicationException(
                    "Ya existe una reserva con este código "
                            +reserva.getIdReserva(), "Id repetido");
        } catch(DataAccessException e) {
            throw new AsenApplicationException(
                    "Error en el acceso a la base de datos", "ErrorAcceder");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{idReserva}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(idReserva));
        return "reserva/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        reservaDao.updateReserva(reserva);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{idReserva}")
    public String processDelete(@PathVariable int idReserva) {
        reservaDao.deleteReserva(idReserva);
        return "redirect:../list";
    }

    @RequestMapping(value = "/updateHoraSalida/{idReserva}", method = RequestMethod.GET)
    public String editHoraSalidaReserva(Model model, @PathVariable int idReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(idReserva));
        return "reserva/updateHoraSalida";
    }

    @RequestMapping(value = "/updateHoraSalida", method = RequestMethod.POST)
    public String processHoraSalidaUpdateSubmit(@ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/updateHoraSalida";
        reservaDao.updateHoraSalidaReserva(reserva);
        return "redirect:list";
    }
}
