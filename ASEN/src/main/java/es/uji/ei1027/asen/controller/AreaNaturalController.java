package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.model.Municipio;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/areaNatural")
public class AreaNaturalController {
    private AreaNaturalDao areaNaturalDao;


    private MostrarOcupacionService mostrarOcupacionService;
    private GetMunicipiosService getMunicipiosService;
    private GetAreasNaturalesService getAreasNaturalesService;
    private GetTiposServicioService getTiposServicioService;
    private GetFranjasHorariasService getFranjasHorariasService;
    @Autowired
    public void setGetFranjasHorariasService(GetFranjasHorariasService getFranjasHorariasService){
        this.getFranjasHorariasService=getFranjasHorariasService;
    }
    @Autowired
    public void setGetTiposServicioService(GetTiposServicioService getTiposServicioService){
        this.getTiposServicioService=getTiposServicioService;
    }

    @Autowired
    public void setMostrarOcupacionService(MostrarOcupacionService mostrarOcupacionService) {
        this.mostrarOcupacionService = mostrarOcupacionService;
    }
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService) {
        this.getMunicipiosService = getMunicipiosService;
    }
    @Autowired
    public void setGetAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService = getAreasNaturalesService;
    }
    @Autowired
    public void setAreaNaturalDao(AreaNaturalDao areaNaturalDao) {
        this.areaNaturalDao=areaNaturalDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listAreasNaturales(Model model, HttpSession session) {
            UserDetails user = (UserDetails) session.getAttribute("user");
            if (user.getRol() == "GestorMunicipal") {
                int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
                model.addAttribute("areasNaturales", getAreasNaturalesService.getAreasPueblo(municipio));
            } else
                model.addAttribute("areasNaturales", areaNaturalDao.getAreasNaturales());
        return "areaNatural/list";
    }
    @RequestMapping("/listNoRegister")
    public String listAreasNaturalesNoRegister(Model model, HttpSession session) {
        model.addAttribute("areasNaturales", areaNaturalDao.getAreasNaturales());
        session.setAttribute("user", new UserDetails());
        return "areaNatural/listNoRegister";
    }

    @RequestMapping(value="/add")
    public String addAreaNatural(Model model, HttpSession session) {
        model.addAttribute("areaNatural", new AreaNatural());
        UserDetails user = (UserDetails) session.getAttribute("user");
        Municipio municipio = getMunicipiosService.getObjetoMunicipio(getMunicipiosService.getMunicipioGestor(user.getUsername()));
        model.addAttribute("municipio", municipio);
        return "areaNatural/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("areaNatural") AreaNatural areaNatural,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "areaNatural/add";
        areaNaturalDao.addAreaNatural(areaNatural);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idArea}", method = RequestMethod.GET)
    public String editAreaNatural(Model model, @PathVariable int idArea) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        return "areaNatural/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("areaNatural") AreaNatural areaNatural,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "areaNatural/update";
        areaNaturalDao.updateAreaNatural(areaNatural);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idArea}")
    public String processDelete(@PathVariable int idArea, RedirectAttributes redirectAttrs) {
        try {
            areaNaturalDao.deleteAreaNatural(idArea);
        }catch(DataIntegrityViolationException e){
            redirectAttrs
                    .addFlashAttribute("mensaje", "No se puede eliminar el area, pues tiene zonas asignadas")
                    .addFlashAttribute("clase", "error");
            return "redirect:../list";
        }
        return "redirect:../list";
    }

    @RequestMapping(value="/consultarOcupacion/{idArea}")
    public String consultarOcupacion(Model model, @PathVariable int idArea) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        model.addAttribute("municipio", mostrarOcupacionService.getMunicipiofromAreaNatural(idArea));
        model.addAttribute("mostrarOcupacionSvc", mostrarOcupacionService);
        model.addAttribute("servicios", getTiposServicioService.getServiciosArea(idArea));
        model.addAttribute("TipoServicioService", getTiposServicioService);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        return "areaNatural/consultarOcupacion";
    }

}
