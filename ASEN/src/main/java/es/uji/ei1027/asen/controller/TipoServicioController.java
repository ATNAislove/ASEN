package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.TipoServicioDao;
import es.uji.ei1027.asen.model.TipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tipoServicio")
public class TipoServicioController {

    private TipoServicioDao tipoServicioDao;

    @Autowired
    public void setTipoServicioDao(TipoServicioDao tipoServicioDao) {
        this.tipoServicioDao = tipoServicioDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listTipoServicios(Model model) {
        model.addAttribute("tipoServicios", tipoServicioDao.getTipoServicios());
        return "tipoServicio/list";
    }
    @RequestMapping(value = "/add")
    public String addTipoServicio(Model model) {
        model.addAttribute("tipoServicio", new TipoServicio());
        return "tipoServicio/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("tipoServicio") TipoServicio tipoServicio,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipoServicio/add";
        tipoServicioDao.addTipoServicio(tipoServicio);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{nombre}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable int idTipoServicio) {
        model.addAttribute("tipoServicio", tipoServicioDao.getTipoServicio(idTipoServicio));
        return "tipoServicio/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("tipoServicio") TipoServicio tipoServicio,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipoServicio/update";
        tipoServicioDao.updateTipoServicio(tipoServicio);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nombre}")
    public String processDelete(@PathVariable int idTipoServicio) {
        tipoServicioDao.deleteTipoServicio(idTipoServicio);
        return "redirect:../list";
    }
}
