package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

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
    @RequestMapping("/menuCiudadano")
    public String menuCiudadano(Model model) {
        return "ciudadano/menuCiudadano";
    }
    @RequestMapping("/list")
    public String listCiudadanos(Model model) {
        model.addAttribute("ciudadanos", ciudadanoDao.getCiudadanos());
        return "ciudadano/list";
    }

    @RequestMapping(value="/add")
    public String addCiudadano(Model model, HttpSession session) {
        session.setAttribute("user", new UserDetails());
        model.addAttribute("ciudadano", new Ciudadano());
        LocalDate maxi = LocalDate.now().minusYears(18);
        model.addAttribute("maxi",maxi);
        return "ciudadano/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ciudadano") Ciudadano ciudadano,
                                   BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        CiudadanoValidator ciudadanoValidator = new CiudadanoValidator();
        ciudadanoValidator.validate(ciudadano,bindingResult);
        if (bindingResult.hasErrors())
            return "ciudadano/add";
        try{
            ciudadanoDao.addCiudadano(ciudadano);
        }
        catch(DuplicateKeyException e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "ya existe un usuario con este nombre de usuario")
                    .addFlashAttribute("clase", "danger");
            return "redirect:add";
        }

        catch(DataAccessException e) {
            throw new AsenApplicationException(
                    "Error en el acceso a la base de datos", "ErrorAcceder","danger");
        }
        return "redirect:../login";
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
    public String processDelete(@PathVariable String usuario, RedirectAttributes redirectAttrs) {
        ciudadanoDao.deleteCiudadano(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/menu")
    public String menuCiudadanos(Model model) {
        return "ciudadano/menu";
    }

}
