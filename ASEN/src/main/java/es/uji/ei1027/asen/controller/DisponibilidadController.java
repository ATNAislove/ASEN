package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.DisponibilidadDao;
import es.uji.ei1027.asen.dao.ServicioDao;
import es.uji.ei1027.asen.model.Disponibilidad;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.GetAreasNaturalesService;
import es.uji.ei1027.asen.svc.GetFranjasHorariasService;
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
@RequestMapping("/disponibilidad")

public class DisponibilidadController {

    private DisponibilidadDao disponibilidadDao;
    private GetTiposServicioService getTiposServicioService;
    private GetMunicipiosService getMunicipiosService;
    private GetFranjasHorariasService getFranjasHorariasService;
    private GetAreasNaturalesService getAreasNaturalesService;
    @Autowired
    public void setAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService=getAreasNaturalesService;
    }
    @Autowired
    public void setGetFranjasHorariasService(GetFranjasHorariasService getFranjasHorariasService){
        this.getFranjasHorariasService=getFranjasHorariasService;
    }

    @Autowired
    public void setGetTiposServicioService(GetTiposServicioService getTiposServicioService){
        this.getTiposServicioService = getTiposServicioService;
    }
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService){
        this.getMunicipiosService=getMunicipiosService;
    }

    @Autowired
    public void setDisponibilidadDao(DisponibilidadDao disponibilidadDao) {
        this.disponibilidadDao=disponibilidadDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listDisponibilidades(Model model) {
        model.addAttribute("disponibilidades", disponibilidadDao.getDisponibilidades());
        return "disponibilidad/list";
    }


    @RequestMapping(value="/update/{idFranjaHoraria}/{idServicio}", method = RequestMethod.GET)
    public String editDisponibilidad(Model model, @PathVariable int idFranjaHoraria) {
        model.addAttribute("disponibilidad", disponibilidadDao.getDisponibilidad(idFranjaHoraria));
        return "disponibilidad/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("disponibilidad") Disponibilidad disponibilidad,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "disponibilidad/update";
        disponibilidadDao.updateDisponibilidad(disponibilidad);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idFranjaHoraria}/{idServicio}")
    public String processDelete(@PathVariable int idFranjaHoraria) {
        disponibilidadDao.deleteDisponibilidad(idFranjaHoraria);
        return "redirect:../list";
    }
    @RequestMapping(value="/verHorarios/{idServicio}")
    public String listHorarios(Model model, @PathVariable int idServicio) {
        model.addAttribute("disponibilidades", getTiposServicioService.getHorariosServicio(idServicio));
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("idServicio",idServicio);
        model.addAttribute("tipoServicio",getTiposServicioService.getTipoServicioDelServicio(idServicio));
        model.addAttribute("area",getAreasNaturalesService.getNombreArea(getTiposServicioService.getServicio(idServicio).getIdArea()));
        return "/disponibilidad/verHorarios";
    }

    /*@RequestMapping(value="/add")
    public String addHorarioServicio(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        model.addAttribute("disponibilidad", new Disponibilidad());
        model.addAttribute("servicios", getTiposServicioService.getServiciosMunicipio(municipio));
        model.addAttribute("getTiposServicio", getTiposServicioService);
        model.addAttribute("franjasHorarias",getFranjasHorariasService.getFranjasServicio());

        return "disponibilidad/add";
    }*/
    @RequestMapping(value="/add/{idServicio}")
    public String addHorarioServicio(Model model,@PathVariable int idServicio, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setIdServicio(idServicio);
        model.addAttribute("disponibilidad", disponibilidad);
        model.addAttribute("servicios", getTiposServicioService.getServiciosMunicipio(municipio));
        //model.addAttribute("getTiposServicio", getTiposServicioService);
        model.addAttribute("franjasHorarias",getFranjasHorariasService.getFranjasServicio());
        model.addAttribute("tipoServicio",getTiposServicioService.getTipoServicioDelServicio(idServicio));
        model.addAttribute("area",getAreasNaturalesService.getNombreArea(getTiposServicioService.getServicio(idServicio).getIdArea()));



        return "disponibilidad/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("disponibilidad") Disponibilidad disponibilidad,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "disponibilidad/add";
        disponibilidadDao.addDisponibilidad(disponibilidad);
        return "redirect:/disponibilidad/verHorarios/"+disponibilidad.getIdServicio();
    }

}
