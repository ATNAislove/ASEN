package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.model.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ciudadano")
public class CiudadanoController {

    private CiudadanoDao ciudadanoDao;

    @Autowired
    public void setCiudadanoDao(CiudadanoDao ciudadanoDao) {
        this.ciudadanoDao = ciudadanoDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listCiudadanos(Model model) {
        model.addAttribute("ciudadanos", ciudadanoDao.getCiudadanos());
        return "ciudadano/list";
    }

    @RequestMapping(value="/add")
    public String addCiudadano(Model model) {
        model.addAttribute("ciudadano", new Ciudadano());
        return "ciudadano/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ciudadano") Ciudadano ciudadano,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ciudadano/add";
        ciudadanoDao.addCiudadano(ciudadano);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        ciudadanoDao.deleteCiudadano(usuario);
        return "redirect:../list";
    }


}
