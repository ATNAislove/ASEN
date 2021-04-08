package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.CiudadanoDao;
import es.uji.ei1027.asen.dao.FranjaHorariaDao;
import es.uji.ei1027.asen.model.Ciudadano;
import es.uji.ei1027.asen.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/franjaHoraria")
public class FranjaHorariaController {
        private FranjaHorariaDao franjaHorariaDao;

        @Autowired
        public void setFranjaHorariaDao(FranjaHorariaDao franjaHorariaDao) {
            this.franjaHorariaDao = franjaHorariaDao;
        }

        // Operacions: Crear, llistar, actualitzar, esborrar
        // ...
        @RequestMapping("/list")
        public String listFranjasHorario(Model model) {
            model.addAttribute("franjasHoraria", franjaHorariaDao.getFranjasHorarias());
            return "franjaHoraria/list";
        }


        @RequestMapping(value = "/add")
        public String addFranjaHoraria(Model model) {
            model.addAttribute("franjasHoraria", new FranjaHoraria());
            return "franjaHoraria/add";
        }


        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public String processAddSubmit(@ModelAttribute("franjaHoraria") FranjaHoraria franjaHoraria,
                                       BindingResult bindingResult) {
            if (bindingResult.hasErrors())
                return "franjaHoraria/add";
            franjaHorariaDao.addFranjaHoraria(franjaHoraria);
            return "redirect:list";
        }

        @RequestMapping(value = "/update/{idFranjaHoraria}", method = RequestMethod.GET)
        public String editFranjaHoraria(Model model, @PathVariable int idFranjaHoraria) {
            model.addAttribute("franjaHoraria", franjaHorariaDao.getFranjaHoraria(idFranjaHoraria));
            return "franjaHoraria/update";
        }

        @RequestMapping(value = "/update", method = RequestMethod.POST)
        public String processUpdateSubmit(@ModelAttribute("franjaHoraria") FranjaHoraria franjaHoraria,
                                          BindingResult bindingResult) {
            if (bindingResult.hasErrors())
                return "franjaHoraria/update";
            franjaHorariaDao.updateFranjaHoraria(franjaHoraria);
            return "redirect:list";
        }
        @RequestMapping(value = "/delete/{idFranjaHoraria}")
        public String processDelete(@PathVariable int idFranjaHoraria) {
            franjaHorariaDao.deleteFranjaHoraria(idFranjaHoraria);
            return "redirect:../list";
        }

}
