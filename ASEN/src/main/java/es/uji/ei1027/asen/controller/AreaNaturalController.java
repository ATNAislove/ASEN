package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.svc.MostrarOcupacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/areaNatural")
public class AreaNaturalController {
    private AreaNaturalDao areaNaturalDao;


    private MostrarOcupacionService mostrarOcupacionService;

    @Autowired
    public void setMostrarOcupacionService(MostrarOcupacionService mostrarOcupacionService) {
        this.mostrarOcupacionService = mostrarOcupacionService;
    }

    @Autowired
    public void setAreaNaturalDao(AreaNaturalDao areaNaturalDao) {
        this.areaNaturalDao=areaNaturalDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listAreasNaturales(Model model) {
        model.addAttribute("areasNaturales", areaNaturalDao.getAreasNaturales());
        return "areaNatural/list";
    }


    @RequestMapping(value="/add")
    public String addAreaNatural(Model model) {
        model.addAttribute("areaNatural", new AreaNatural());
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
    public String processDelete(@PathVariable int idArea) {
        areaNaturalDao.deleteAreaNatural(idArea);
        return "redirect:../list";
    }

    @RequestMapping(value="/consultarOcupacion/{idArea}", method = RequestMethod.GET)
    public String consultarOcupacion(Model model, @PathVariable int idArea) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        model.addAttribute("municipio", mostrarOcupacionService.getMunicipiofromAreaNatural(idArea));
        return "areaNatural/consultarOcupacion";
    }
}
