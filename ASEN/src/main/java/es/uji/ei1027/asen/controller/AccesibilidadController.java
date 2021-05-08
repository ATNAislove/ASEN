package es.uji.ei1027.asen.controller;


import es.uji.ei1027.asen.dao.AccesibilidadDao;
import es.uji.ei1027.asen.dao.MunicipioDao;
import es.uji.ei1027.asen.model.Accesibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/accesibilidad")
public class AccesibilidadController {

    private AccesibilidadDao accesibilidadDao;

    @Autowired
    public void setAccesibilidadDao(AccesibilidadDao accesibilidadDao) {
        this.accesibilidadDao=accesibilidadDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listFranjasHorario(Model model) {
        model.addAttribute("accesibilidades", accesibilidadDao.getAccesibilidades());
        return "accesibilidad/list";
    }


    @RequestMapping(value = "/add")
    public String addFranjaHoraria(Model model) {
        model.addAttribute("accesibilidad", new Accesibilidad());
        return "accesibilidad/add";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("accesibilidad") Accesibilidad accesibilidad,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "accesibilidad/add";
        accesibilidadDao.addAccesibilidad(accesibilidad);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{idFranjaHoraria}/{idArea}", method = RequestMethod.GET)
    public String editAccesibilidad(Model model, @PathVariable int idFranjaHoraria, @PathVariable int idArea) {
        model.addAttribute("accesibilidad", accesibilidadDao.getAccesibilidad(idFranjaHoraria,idArea));
        return "accesibilidad/update";
    }

    /*
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("accesibilidad") FranjaHoraria franjaHoraria,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "accesibilidad/update";
        accesibilidadDao.updateFranjaHoraria(franjaHoraria);
        return "redirect:list";
    }
    */
    @RequestMapping(value = "/delete/{idFranjaHoraria}/{idArea}")
    public String processDelete(@PathVariable int idFranjaHoraria,@PathVariable int idArea) {
        accesibilidadDao.deleteAccesibilidad(idFranjaHoraria,idArea);
        return "redirect:../list";
    }
}
