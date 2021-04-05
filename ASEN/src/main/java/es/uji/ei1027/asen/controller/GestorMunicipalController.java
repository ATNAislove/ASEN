package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.GestorMunicipalDao;
import es.uji.ei1027.asen.model.GestorMunicipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/gestorMunicipal")
public class GestorMunicipalController {
    private GestorMunicipalDao gestorMunicipalDao;

    @Autowired
    public void setGestorMunicipalDao(GestorMunicipalDao gestorMunicipalDao) {
        this.gestorMunicipalDao=gestorMunicipalDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listGestoresMunicipales(Model model) {
        model.addAttribute("gestoresMunicipales", gestorMunicipalDao.getGestoresMunicipales());
        return "gestorMunicipal/list";
    }


    @RequestMapping(value="/add")
    public String addGestorMunicipal(Model model) {
        model.addAttribute("gestorMunicipal", new GestorMunicipal());
        return "gestorMunicipal/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("gestorMunicipal") GestorMunicipal gestorMunicipal,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "gestorMunicipal/add";
        gestorMunicipalDao.addGestorMunicipal(gestorMunicipal);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editGestorMunicipal(Model model, @PathVariable String dni) {
        model.addAttribute("gestorMunicipal", gestorMunicipalDao.getGestorMunicipal(dni));
        return "gestorMunicipal/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("gestorMunicipal") GestorMunicipal gestorMunicipal,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "gestorMunicipal/update";
        gestorMunicipalDao.updateGestorMunicipal(gestorMunicipal);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String dni) {
        gestorMunicipalDao.deleteGestorMunicipal(dni);
        return "redirect:../list";
    }
}
