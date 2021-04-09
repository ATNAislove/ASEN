package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.VigilanciaDao;
import es.uji.ei1027.asen.model.Vigilancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/vigilancia")

public class VigilanciaController {

    private VigilanciaDao vigilanciaDao;

    @Autowired
    public void setVigilanciaDao(VigilanciaDao vigilanciaDao) {
        this.vigilanciaDao=vigilanciaDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listVigilancias(Model model) {
        model.addAttribute("vigilancias", vigilanciaDao.getVigilancias());
        return "vigilancia/list";
    }


    @RequestMapping(value="/add")
    public String addVigilancia(Model model) {
        model.addAttribute("vigilancia", new Vigilancia());
        return "vigilancia/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("vigilancia") Vigilancia vigilancia,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "vigilancia/add";
        vigilanciaDao.addVigilancia(vigilancia);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editVigilancia(Model model, @PathVariable String dni) {
        model.addAttribute("vigilancia", vigilanciaDao.getVigilancia(dni));
        return "vigilancia/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("vigilancia") Vigilancia vigilancia,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "vigilancia/update";
        vigilanciaDao.updateVigilancia(vigilancia);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        vigilanciaDao.deleteVigilancia(dni);
        return "redirect:../list";
    }

}
