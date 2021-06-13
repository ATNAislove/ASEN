package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Reserva;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.GetFranjasHorariasService;
import es.uji.ei1027.asen.svc.ReservaService;
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
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpSession;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/reserva")
public class ReservaController{

    private ReservaDao reservaDao;
    private ReservaService reservaService;
    private GetFranjasHorariasService getFranjasHorariasService;
    @Autowired
    public void setGetFranjasHorariasService(GetFranjasHorariasService getFranjasHorariasService){
        this.getFranjasHorariasService=getFranjasHorariasService;
    }
    @Autowired
    public void setReservaService(ReservaService reservaService){this.reservaService=reservaService;}
    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listReservas(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if(user.getRol()=="Ciudadano") {
            model.addAttribute("reservas", reservaDao.getReservasUsuario(user.getUsername()));

        }else{
            model.addAttribute("reservas", reservaDao.getReservas());
        }
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        return "reserva/list";
    }
    @RequestMapping(value = "/add/{idZona}", method = RequestMethod.GET)
    public String addReserva(Model model, @PathVariable int idZona, HttpSession session) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        session.setAttribute("zona", idZona);
        return "reserva/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult,HttpSession session) {
        ReservaValidator reservaValidator = new ReservaValidator();
        reservaValidator.validate(reserva, bindingResult);
        if (bindingResult.hasErrors()) {
            return "reserva/add";
        }
        try {
            reservaDao.addReserva(reserva, session);
            int zona = (int) session.getAttribute("zona");
            reservaService.addOcupacion(reserva.getIdReserva(), zona);
        } catch (DuplicateKeyException e) {
            throw new AsenApplicationException(
                    "Ya existe una reserva con este código "
                            + reserva.getIdReserva(), "Id repetido");
        } catch (DataAccessException e) {
            System.out.println(session.getAttribute("user"));
            throw new AsenApplicationException(
                    "Error en el acceso a la base de datos", "ErrorAcceder");
        } catch (TemplateInputException e) {
            throw new AsenApplicationException("Escribe una fecha posterior a la actual ", "errorfecha");
        }

        return "redirect:list";
    }

    @RequestMapping(value = "/update/{idReserva}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(idReserva));
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        return "reserva/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        UserDetails user = (UserDetails) session.getAttribute("user");
        if(user.getRol()=="Ciudadano")
            reservaDao.updateReserva(reserva);
        else
            reservaDao.updateReservaEstado(reserva);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{idReserva}")
    public String processDelete(@PathVariable int idReserva) {
        reservaService.deleteOcupacion(idReserva);
        reservaDao.deleteReserva(idReserva);
        return "redirect:../list";
    }

    @RequestMapping(value = "/updateHoraSalida/{idReserva}")
    public String editHoraSalidaReserva(@PathVariable int idReserva) {
        reservaDao.updateHoraSalidaReserva(idReserva);
        return "redirect:../list";
    }

}
