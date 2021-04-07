package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.PersonalZonaDao;
import es.uji.ei1027.asen.dao.ServicioDao;
import es.uji.ei1027.asen.model.PersonalZona;
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
@RequestMapping("/personalZona")
public class PersonalZonaController {
    private PersonalZonaDao personalZonaDao;

    @Autowired
    public void setPersonalZonaDao(PersonalZonaDao personalZonaDao) {
        this.personalZonaDao=personalZonaDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listPersonalZona(Model model) {
        model.addAttribute("personalZona", personalZonaDao.getPersonalZonas());
        return "personalZona/list";
    }


    @RequestMapping(value="/add")
    public String addPersonalZona(Model model) {
        model.addAttribute("personalZona", new PersonalZona());
        return "personalZona/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("personalZona") PersonalZona personalZona,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "personalZona/add";
        personalZonaDao.addPersonalZona(personalZona);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editPersonalZona(Model model, @PathVariable String dni) {
        model.addAttribute("personalZona", personalZonaDao.getPersonalZona(dni));
        return "personalZona/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("personalZona") PersonalZona personalZona,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "personalZona/update";
        personalZonaDao.updatePersonalZona(personalZona);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        personalZonaDao.deletePersonalZona(dni);
        return "redirect:../list";
    }
}
