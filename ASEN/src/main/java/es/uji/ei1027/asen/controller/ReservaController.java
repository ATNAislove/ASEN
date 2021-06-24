package es.uji.ei1027.asen.controller;

import com.google.zxing.WriterException;
import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.FiltroOcupacion;
import es.uji.ei1027.asen.model.Reserva;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.GeneradorQRService;
import es.uji.ei1027.asen.svc.GeneradorQRSvc;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/reserva")
public class ReservaController{

    private ReservaDao reservaDao;
    private ReservaService reservaService;
    private GetFranjasHorariasService getFranjasHorariasService;
    private GeneradorQRService generadorQRService;
    @Autowired
    public void setGeneradorQRService(GeneradorQRService generadorQRService){
        this.generadorQRService=generadorQRService;
    }
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
        model.addAttribute("reservaService", reservaService);
        return "reserva/list";
    }
    @RequestMapping(value = "/add/{idZona}", method = RequestMethod.GET)
    public String addReserva(Model model, @PathVariable int idZona, HttpSession session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("maxPersonas", reservaService.maxPersonas(idZona));
        session.setAttribute("zona", idZona);
        FiltroOcupacion filtro = new FiltroOcupacion();
        filtro.setIdZona(idZona);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("filtro",filtro);
        return "reserva/add";
    }
    @RequestMapping(value = "/add/{idZona}/{fecha}", method = RequestMethod.GET)
    public String addReservaFecha(Model model, @PathVariable int idZona,@PathVariable String fecha, HttpSession session) {
        Reserva reserva = new Reserva();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        reserva.setFecha(LocalDate.parse(fecha, formatter));
        model.addAttribute("reserva", reserva);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("maxPersonas", reservaService.maxPersonas(idZona));
        session.setAttribute("zona", idZona);
        FiltroOcupacion filtro = new FiltroOcupacion();
        filtro.setIdZona(idZona);
        filtro.setFecha(LocalDate.parse(fecha, formatter));
        model.addAttribute("filtro",filtro);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("dia",fecha);
        return "reserva/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult,HttpSession session) {
        try {
            ReservaValidator reservaValidator = new ReservaValidator();
            int zona = (int) session.getAttribute("zona");
            reserva.setIdZona(zona);
            reserva.setUsuario(session.getAttribute("user").toString());
            reservaValidator.validate(reserva, bindingResult);
            if (bindingResult.hasErrors()) {
                return "reserva/add/{"+zona+"}";
            }
            if(reservaService.existeReserva(reserva)) {
                throw new AsenApplicationException(
                        "Ya existe una reserva para esa hora en esa zona ", "Reserva repetida","danger");
            }
            String datos = "zona="+reserva.getIdZona()+"; fecha="+reserva.getFecha()+"; franja="+reserva.getIdFranjaHoraria();
            reserva.setCodigoQR(generadorQRService.crearQR(datos,100,100));
            reservaDao.addReserva(reserva);

        } catch (DuplicateKeyException e) {
            throw new AsenApplicationException(
                    "Ya existe una reserva con este código "
                            + reserva.getIdReserva(), "Id repetido","danger");
        } catch (DataAccessException e) {
            System.out.println(session.getAttribute("user"));
            throw new AsenApplicationException(
                    "Error en el acceso a la base de datos", "ErrorAcceder","danger");
        } catch (TemplateInputException e) {
            throw new AsenApplicationException("Escribe una fecha posterior a la actual ", "errorfecha","danger");
        }catch(WriterException e){
            throw new AsenApplicationException("Error al crear al QR", "errorfecha","danger");
        }catch(IOException e){
            throw new AsenApplicationException("Error al convertir el QR ", "errorQR","warning");
        }

        return "redirect:list";
    }

    /*@RequestMapping(value = "/update/{idReserva}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idReserva) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Reserva reserva = reservaDao.getReserva(idReserva);
        model.addAttribute("reserva", reserva);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("reservaService", reservaService);
        FiltroOcupacion filtro = new FiltroOcupacion();
        filtro.setIdReserva(idReserva);
        filtro.setIdZona(reserva.getIdZona());

        filtro.setFecha(reserva.getFecha());

        model.addAttribute("filtro",filtro);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("maxPersonas", reservaService.maxPersonas(reserva.getIdZona()));
        return "reserva/update";
    }*/
    @RequestMapping(value = "/update/{idReserva}/{fecha}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idReserva,@PathVariable String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Reserva reserva = reservaDao.getReserva(idReserva);
        reserva.setFecha(LocalDate.parse(fecha, formatter));
        model.addAttribute("reserva", reserva);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("reservaService", reservaService);
        model.addAttribute("maxPersonas", reservaService.maxPersonas(reserva.getIdZona()));
        FiltroOcupacion filtro = new FiltroOcupacion();
        filtro.setIdReserva(idReserva);
        filtro.setIdZona(reserva.getIdZona());
        filtro.setFecha(LocalDate.parse(fecha, formatter));
        if(reserva.getFecha().equals(reservaDao.getReserva(idReserva).getFecha()))
            model.addAttribute("franjaActual",reserva.getIdFranjaHoraria());
        model.addAttribute("filtro",filtro);
        model.addAttribute("dia",fecha);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        return "reserva/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        UserDetails user = (UserDetails) session.getAttribute("user");
        try{
            String datos = "zona="+reserva.getIdZona()+"; fecha="+reserva.getFecha()+"; franja="+reserva.getIdFranjaHoraria();
            reserva.setCodigoQR(generadorQRService.crearQR(datos,100,100));
        }catch(WriterException e){
            throw new AsenApplicationException("Error al crear al QR", "errorfecha","danger");
        }catch(IOException e){
            throw new AsenApplicationException("Error al convertir el QR ", "errorQR","warning");
        }
        if(user.getRol()=="Ciudadano")
            reservaDao.updateReserva(reserva);
        else
            reservaDao.updateReservaEstado(reserva);
        return "redirect:list";
    }
    @RequestMapping(value="/cancel/{idReserva}")
    public String processCancel(@PathVariable int idReserva){
        reservaDao.cancelarReserva(idReserva);
        return "redirect:../list";
    }
    @RequestMapping(value = "/delete/{idReserva}")
    public String processDelete(@PathVariable int idReserva) {
        reservaDao.deleteReserva(idReserva);
        return "redirect:../list";
    }

    @RequestMapping(value = "/updateHoraSalida/{idReserva}")
    public String editHoraSalidaReserva(@PathVariable int idReserva) {
        reservaDao.updateHoraSalidaReserva(idReserva);
        return "redirect:../list";
    }
    @RequestMapping(value="/fecha", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarFechaReserva(@ModelAttribute FiltroOcupacion filtro){
        return "redirect:add/"+filtro.getIdZona()+"/"+filtro.getFecha();
    }
    @RequestMapping(value="/fechaUpdate", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarFechaReservaUpdate(@ModelAttribute FiltroOcupacion filtro){
        return "redirect:update/"+filtro.getIdReserva()+"/"+filtro.getFecha();
    }

}
