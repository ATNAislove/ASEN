package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.DisponibilidadDao;
import es.uji.ei1027.asen.model.Disponibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/disponibilidad")

public class DisponibilidadController {

    private DisponibilidadDao disponibilidadDao;

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


    @RequestMapping(value="/add")
    public String addDisponibilidad(Model model) {
        model.addAttribute("disponibilidad", new Disponibilidad());
        return "disponibilidad/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("disponibilidad") Disponibilidad disponibilidad,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "disponibilidad/add";
        disponibilidadDao.addvDisponibilidad(disponibilidad);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idFranjaHoraria}", method = RequestMethod.GET)
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

    @RequestMapping(value="/delete/{idFranjaHoraria}")
    public String processDelete(@PathVariable int idFranjaHoraria) {
        disponibilidadDao.deleteDisponibilidad(idFranjaHoraria);
        return "redirect:../list";
    }

}
