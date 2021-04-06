package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.MunicipioDao;
import es.uji.ei1027.asen.dao.ServicioDao;
import es.uji.ei1027.asen.model.Municipio;
import es.uji.ei1027.asen.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    private ServicioDao servicioDao;

    @Autowired
    public void setServicioDao(ServicioDao servicioDao) {
        this.servicioDao=servicioDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listServicios(Model model) {
        model.addAttribute("servicios", servicioDao.getServicios());
        return "servicio/list";
    }


    @RequestMapping(value="/add")
    public String addServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicio/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("servicio") Servicio servicio,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "servicio/add";
        servicioDao.addServicio(servicio);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idServicio}", method = RequestMethod.GET)
    public String editServicio(Model model, @PathVariable int idServicio) {
        model.addAttribute("servicio", servicioDao.getServicio(idServicio));
        return "servicio/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("servicio") Servicio servicio,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "servicio/update";
        servicioDao.updateServicio(servicio);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idServicio}")
    public String processDelete(@PathVariable int idServicio) {
        servicioDao.deleteServicio(idServicio);
        return "redirect:../list";
    }
}
