package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.ZonaDao;
import es.uji.ei1027.asen.model.Municipio;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.model.Zona;
import es.uji.ei1027.asen.svc.GetAreasNaturalesService;
import es.uji.ei1027.asen.svc.GetMunicipiosService;
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
@RequestMapping("/zona")
public class ZonaController {
    private ZonaDao zonaDao;
    private GetAreasNaturalesService getAreasNaturalesService;
    private GetMunicipiosService getMunicipiosService;

    @Autowired
    public void setZonaDao(ZonaDao zonaDao) {
        this.zonaDao=zonaDao;
    }

    @Autowired
    public void setGetAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService = getAreasNaturalesService;
    }
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService) {
        this.getMunicipiosService = getMunicipiosService;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping(value="/list/{idArea}", method = RequestMethod.GET)
    public String listZonas(Model model,@PathVariable int idArea) {
        model.addAttribute("zonas", zonaDao.getZonasByArea(idArea));
        model.addAttribute("area",getAreasNaturalesService.getNombreArea(idArea));
        return "zona/list";
    }


    @RequestMapping(value="/add")
    public String addZona(Model model, HttpSession session) {
        model.addAttribute("zona", new Zona());
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        model.addAttribute("areas", getAreasNaturalesService.getAreasPueblo(municipio));
        return "zona/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("zona") Zona zona,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "zona/add";
        zonaDao.addZona(zona);
        return "redirect:../zona/list/"+ zona.getIdArea();
    }

    @RequestMapping(value="/update/{idZona}", method = RequestMethod.GET)
    public String editZona(Model model, @PathVariable int idZona, HttpSession session) {
        model.addAttribute("zona", zonaDao.getZona(idZona));
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        model.addAttribute("areas", getAreasNaturalesService.getAreasPueblo(municipio));
        return "zona/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("zona") Zona zona,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "zona/update";
        zonaDao.updateZona(zona);
        return "redirect:../zona/list/"+ zona.getIdArea();
    }

    @RequestMapping(value="/delete/{idZona}")
    public String processDelete(@PathVariable int idZona, RedirectAttributes redirectAttrs) {
        int area = getAreasNaturalesService.getIdArea(idZona);
        try {
            zonaDao.deleteZona(idZona);
            return "redirect:../list/" + area;
        }catch(DataIntegrityViolationException e){
            redirectAttrs
                    .addFlashAttribute("mensaje", "No se puede eliminar la zona, pues tiene reservas asignadas")
                    .addFlashAttribute("clase", "error");
            return "redirect:../list/" + area;
        }
    }
}
