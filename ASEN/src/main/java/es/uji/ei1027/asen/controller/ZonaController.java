package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.ZonaDao;
import es.uji.ei1027.asen.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/zona")
public class ZonaController {
    private ZonaDao zonaDao;

    @Autowired
    public void setZonaDao(ZonaDao zonaDao) {
        this.zonaDao=zonaDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping(value="/list/{idArea}", method = RequestMethod.GET)
    public String listZonas(Model model,@PathVariable int idArea) {
        model.addAttribute("zonas", zonaDao.getZonasByArea(idArea));
        return "zona/list";
    }


    @RequestMapping(value="/add")
    public String addZona(Model model) {
        model.addAttribute("zona", new Zona());
        return "zona/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("zona") Zona zona,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "zona/add";
        zonaDao.addZona(zona);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idZona}", method = RequestMethod.GET)
    public String editZona(Model model, @PathVariable int idZona) {
        model.addAttribute("zona", zonaDao.getZona(idZona));
        return "zona/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("zona") Zona zona,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "zona/update";
        zonaDao.updateZona(zona);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idZona}")
    public String processDelete(@PathVariable int idZona) {
        zonaDao.deleteZona(idZona);
        return "redirect:../list";
    }
}
