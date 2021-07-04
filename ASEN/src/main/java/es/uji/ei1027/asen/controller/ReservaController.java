package es.uji.ei1027.asen.controller;

import com.google.zxing.WriterException;
import es.uji.ei1027.asen.dao.ReservaDao;
import es.uji.ei1027.asen.model.*;
import es.uji.ei1027.asen.svc.GeneradorQRService;
import es.uji.ei1027.asen.svc.GetFranjasHorariasService;
import es.uji.ei1027.asen.svc.GetMunicipiosService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/reserva")
public class ReservaController{

    private ReservaDao reservaDao;
    private ReservaService reservaService;
    private GetFranjasHorariasService getFranjasHorariasService;
    private GeneradorQRService generadorQRService;
    private GetMunicipiosService getMunicipiosService;
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService){
        this.getMunicipiosService=getMunicipiosService;
    }
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

        }else if(user.getRol()=="GestorMunicipal"){
            model.addAttribute("reservas",reservaDao.getReservasMunicipio(getMunicipiosService.getMunicipioGestor(user.getUsername())));
        }else{
            model.addAttribute("reservas", reservaDao.getReservas());

        }
        session.removeAttribute("filtro");
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("reservaService", reservaService);
        return "reserva/list";
    }
    @RequestMapping(value = "/add/{idArea}", method = RequestMethod.GET)
    public String addReserva(Model model, @PathVariable int idArea, HttpSession session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        FiltroOcupacion filtro;
        if(session.getAttribute("filtro")!=null){
            filtro = (FiltroOcupacion) session.getAttribute("filtro");
            filtro.setIdArea(idArea);
        }else{
            filtro = new FiltroOcupacion();
            filtro.setIdArea(idArea);
        }
        if(filtro.getZonas()!= null && filtro.getZonas().size()>0){
            model.addAttribute("maxpersonas",reservaService.maxPersonas(filtro.getZonas()));
        }else{
            model.addAttribute("maxpersonas",reservaService.maxPersonas(idArea));
        }
        model.addAttribute("reservaService",reservaService);

        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("filtro",filtro);
        return "reserva/add";
    }
    @RequestMapping(value = "/add/{idArea}/{fecha}", method = RequestMethod.GET)
    public String addReservaFecha(Model model, @PathVariable int idArea,@PathVariable String fecha, HttpSession session) {
        Reserva reserva = new Reserva();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        reserva.setFecha(LocalDate.parse(fecha, formatter));
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);

        model.addAttribute("reservaService",reservaService);
        FiltroOcupacion filtro;
        if(session.getAttribute("filtro")!=null){
            filtro = (FiltroOcupacion) session.getAttribute("filtro");
            filtro.setIdArea(idArea);
            reserva.setIdFranjaHoraria(filtro.getIdFranjaHoraria());
        }else{
            filtro = new FiltroOcupacion();
            filtro.setIdArea(idArea);
        }
        if(filtro.getZonas()!= null && filtro.getZonas().size()>0){
            reserva.setZonas(filtro.getZonas());
            model.addAttribute("maxpersonas",reservaService.maxPersonas(filtro.getZonas()));
        }else{
            model.addAttribute("maxpersonas",reservaService.maxPersonas(idArea));
        }
        filtro.setFecha(LocalDate.parse(fecha, formatter));
        model.addAttribute("filtro",filtro);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("dia",fecha);
        model.addAttribute("reserva", reserva);
        return "reserva/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttrs) {
        try {
            ReservaValidator reservaValidator = new ReservaValidator();

            reserva.setUsuario(session.getAttribute("user").toString());
            reservaValidator.validate(reserva, bindingResult);
            FiltroOcupacion filtro = (FiltroOcupacion) session.getAttribute("filtro");
            if (bindingResult.hasErrors()) {
                return "reserva/add/{"+filtro.getIdArea()+"}";
            }
            /*if(reservaService.existeReserva(reserva)) {
                throw new AsenApplicationException(
                        "Ya existe una reserva para esa hora en esa zona ", "Reserva repetida","danger");
            }*/
            String datos = "fecha="+reserva.getFecha()+"; franja="+reserva.getIdFranjaHoraria()+"; zonas="+reserva.getZonas().toString();
            reserva.setCodigoQR(generadorQRService.crearQR(datos,100,100));
            int idReserva = reservaDao.addReserva(reserva);
            if(idReserva>=0) {
                for (int i : reserva.getZonas()) {
                    reservaService.insertarOcupacion(idReserva, i);
                }
            }
            redirectAttrs
                    .addFlashAttribute("mensaje", "Se ha creado la reserva correctamente")
                    .addFlashAttribute("clase", "success");
            session.removeAttribute("filtro");
        } catch (DuplicateKeyException e) {
            throw new AsenApplicationException(
                    "Ya existe una reserva con este código "
                            + reserva.getIdReserva(), "Id repetido","danger");
        } catch (DataAccessException e) {
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

    @RequestMapping(value = "/update/{idReserva}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idReserva,HttpSession session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Reserva reserva = reservaDao.getReserva(idReserva);
        reserva.setZonas(reservaService.listaZonas(reserva.getIdReserva()));
        //model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("reservaService", reservaService);
        FiltroOcupacion filtro;

        if(session.getAttribute("filtro")!=null){
            filtro = (FiltroOcupacion) session.getAttribute("filtro");

        }else{
            filtro = new FiltroOcupacion();
            filtro.setIdReserva(idReserva);
            filtro.setIdArea(reservaService.recuperarArea(idReserva));
            filtro.setIdFranjaHoraria(reserva.getIdFranjaHoraria());

        }
        if(filtro.getZonas()!= null && filtro.getZonas().size()>0){
            reserva.setZonas(filtro.getZonas());

            model.addAttribute("maxpersonas",reservaService.maxPersonas(filtro.getZonas()));
        }else{
            session.setAttribute("zonasOriginales",reserva.getZonas());
            filtro.setZonas(reserva.getZonas());
            model.addAttribute("maxpersonas",reservaService.maxPersonas(reserva.getZonas()));
        }


        //filtro.setFecha(reserva.getFecha());

        model.addAttribute("filtro",filtro);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        model.addAttribute("reserva", reserva);
        return "reserva/update";
    }
    /*@RequestMapping(value = "/update/{idReserva}/{fecha}", method = RequestMethod.GET)
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
    }*/

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult,
                                      HttpSession session, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        UserDetails user = (UserDetails) session.getAttribute("user");
        try{
            String datos = "fecha="+reserva.getFecha()+"; franja="+reserva.getIdFranjaHoraria()+"; zonas="+reserva.getZonas().toString();
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
        if(reserva.getZonas().size()>0) {
            reservaService.borrarOcupaciones(reserva.getIdReserva());
            for(int zona : reserva.getZonas()){
                reservaService.insertarOcupacion(reserva.getIdReserva(),zona);
            }
        }
        redirectAttrs
                .addFlashAttribute("mensaje", "Se ha modificado la reserva correctamente")
                .addFlashAttribute("clase", "success");
        session.removeAttribute("filtro");
        return "redirect:list";
    }
    @RequestMapping(value="/cancel/{idReserva}")
    public String processCancel(@PathVariable int idReserva, RedirectAttributes redirectAttrs){
        reservaDao.cancelarReserva(idReserva);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se ha cancelado la reserva correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:../list";
    }
    @RequestMapping(value = "/delete/{idReserva}")
    public String processDelete(@PathVariable int idReserva) {
        reservaDao.deleteReserva(idReserva);
        return "redirect:../list";
    }

    @RequestMapping(value = "/updateHoraSalida/{idReserva}")
    public String editHoraSalidaReserva(@PathVariable int idReserva, RedirectAttributes redirectAttrs) {
        reservaDao.updateHoraSalidaReserva(idReserva);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se ha actualizado la hora de salida correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:../list";
    }
    @RequestMapping(value="/fecha", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarFechaReserva(@ModelAttribute FiltroOcupacion filtro, HttpSession session){
        session.setAttribute("filtro",filtro);
        return "redirect:add/"+filtro.getIdArea()+"/"+filtro.getFecha();
    }
    @RequestMapping(value="/zonaUpdate", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarFechaReservaUpdate(@ModelAttribute FiltroOcupacion filtro, HttpSession session){
        //return "redirect:update/"+filtro.getIdReserva()+"/"+filtro.getFecha();
        session.setAttribute("filtro", filtro);
        return "redirect:update/"+filtro.getIdReserva();
    }
    @RequestMapping(value="/zonas", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarZonas(@ModelAttribute FiltroOcupacion filtro, HttpSession session){
        session.setAttribute("filtro",filtro);
        if(filtro.getFecha()!=null)
            return "redirect:add/"+filtro.getIdArea()+"/"+filtro.getFecha();
        return "redirect:add/"+filtro.getIdArea();

    }


}
