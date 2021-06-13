package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.ServicioDao;
import es.uji.ei1027.asen.model.Servicio;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.GetAreasNaturalesService;
import es.uji.ei1027.asen.svc.GetMunicipiosService;
import es.uji.ei1027.asen.svc.GetTiposServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    private ServicioDao servicioDao;

    private GetTiposServicioService getTiposServicioService;
    private GetAreasNaturalesService getAreasNaturalesService;
    private GetMunicipiosService getMunicipiosService;
    @Autowired
    public void setServicioDao(ServicioDao servicioDao) {
        this.servicioDao=servicioDao;
    }

    @Autowired
    public void setGetTiposServicioService(GetTiposServicioService getTiposServicioService){
        this.getTiposServicioService = getTiposServicioService;
    }
    @Autowired
    public void setGetAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService = getAreasNaturalesService;
    }
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService){
        this.getMunicipiosService=getMunicipiosService;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listServicios(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        model.addAttribute("servicios", getTiposServicioService.getServiciosMunicipio(municipio));
        model.addAttribute("areaNaturalService",getAreasNaturalesService);
        model.addAttribute("getTiposServicioService",getTiposServicioService);
        return "servicio/list";
    }


    @RequestMapping(value="/add")
    public String addServicio(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("tiposServicio",getTiposServicioService.getTiposServicioService());
        model.addAttribute("areasNaturales",getAreasNaturalesService.getAreasPueblo(municipio));
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
