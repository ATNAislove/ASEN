package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.OcupacionDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.Ocupacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/ocupacion")
public class OcupacionController {
    private OcupacionDao ocupacionDao;

    @Autowired
    public void setOcupacionDao(OcupacionDao ocupacionDao) {
        this.ocupacionDao = ocupacionDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listOcupaciones(Model model) {
        model.addAttribute("ocupaciones", ocupacionDao.getOcupacions());
        return "ocupacion/list";
    }

    @RequestMapping(value = "/add")
    public String addOcupacion(Model model) {
        model.addAttribute("ocupacion", new Ocupacion());
        return "ocupacion/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ocupacion") Ocupacion ocupacion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ocupacion/add";
        ocupacionDao.addOcupacio(ocupacion);
        return "redirect:list";
    }

    //Hay que hacer update?
   /* @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editCiudadano(Model model, @PathVariable String usuario) {
        model.addAttribute("ciudadano", ciudadanoDao.getCiudadano(usuario));
        return "ciudadano/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("ciudadano") Ciudadano ciudadano,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ciudadano/update";
        ciudadanoDao.updateCiudadano(ciudadano);
        return "redirect:list";
    }
*/

    @RequestMapping(value = "/delete/{idReserva}/{idCharNum}")
    public String processDelete(@PathVariable int idReserva, @PathVariable String idCharNum) {
        ocupacionDao.deleteOcupacio(idReserva,idCharNum);
        return "redirect:../list";
    }

}