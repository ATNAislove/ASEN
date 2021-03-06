package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.AreaNaturalDao;
import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.model.FiltroOcupacion;
import es.uji.ei1027.asen.model.Municipio;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/areaNatural")
public class AreaNaturalController {
    private AreaNaturalDao areaNaturalDao;


    private MostrarOcupacionService mostrarOcupacionService;
    private GetMunicipiosService getMunicipiosService;
    private GetAreasNaturalesService getAreasNaturalesService;
    private GetTiposServicioService getTiposServicioService;
    private GetFranjasHorariasService getFranjasHorariasService;
    @Autowired
    public void setGetFranjasHorariasService(GetFranjasHorariasService getFranjasHorariasService){
        this.getFranjasHorariasService=getFranjasHorariasService;
    }
    @Autowired
    public void setGetTiposServicioService(GetTiposServicioService getTiposServicioService){
        this.getTiposServicioService=getTiposServicioService;
    }

    @Autowired
    public void setMostrarOcupacionService(MostrarOcupacionService mostrarOcupacionService) {
        this.mostrarOcupacionService = mostrarOcupacionService;
    }
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService) {
        this.getMunicipiosService = getMunicipiosService;
    }
    @Autowired
    public void setGetAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService = getAreasNaturalesService;
    }
    @Autowired
    public void setAreaNaturalDao(AreaNaturalDao areaNaturalDao) {
        this.areaNaturalDao=areaNaturalDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listAreasNaturales(Model model, HttpSession session) {
            UserDetails user = (UserDetails) session.getAttribute("user");
            model.addAttribute("AreaNaturalService",getAreasNaturalesService);
        if(user==null || user.getRol()==null){
            session.setAttribute("user", new UserDetails());
            model.addAttribute("areasNaturales", areaNaturalDao.getAreasNaturales());
        }else {
            if (user.getRol() == "GestorMunicipal") {
                int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
                model.addAttribute("areasNaturales", getAreasNaturalesService.getAreasPueblo(municipio));
            } else
                model.addAttribute("areasNaturales", areaNaturalDao.getAreasNaturales());
        }
        session.removeAttribute("filtro");
        return "areaNatural/list";
    }


    @RequestMapping(value="/add")
    public String addAreaNatural(Model model, HttpSession session) {
        model.addAttribute("areaNatural", new AreaNatural());
        UserDetails user = (UserDetails) session.getAttribute("user");
        Municipio municipio = getMunicipiosService.getObjetoMunicipio(getMunicipiosService.getMunicipioGestor(user.getUsername()));
        model.addAttribute("municipio", municipio);
        return "areaNatural/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("areaNatural") AreaNatural areaNatural,
                                   BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors())
            return "areaNatural/add";
        areaNaturalDao.addAreaNatural(areaNatural);
        redirectAttrs
                    .addFlashAttribute("mensaje", "Se ha creado el ??rea correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idArea}", method = RequestMethod.GET)
    public String editAreaNatural(Model model, @PathVariable int idArea) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        model.addAttribute("areasNaturalesService", getAreasNaturalesService);

        return "areaNatural/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("areaNatural") AreaNatural areaNatural,
                                      BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors())
            return "areaNatural/update";
        areaNaturalDao.updateAreaNatural(areaNatural);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se ha modificado el ??rea  correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idArea}")
    public String processDelete(@PathVariable int idArea, RedirectAttributes redirectAttrs) {
        try {
            areaNaturalDao.deleteAreaNatural(idArea);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Se ha eliminado el ??rea correctamente")
                    .addFlashAttribute("clase", "success");
        }catch(DataIntegrityViolationException e){
            redirectAttrs
                    .addFlashAttribute("mensaje", "No se puede eliminar el area, pues tiene zonas asignadas")
                    .addFlashAttribute("clase", "danger");
            return "redirect:../list";
        }
        return "redirect:../list";
    }

    @RequestMapping(value="/consultarOcupacion/{idArea}")
    public String consultarOcupacion(Model model, @PathVariable int idArea) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        model.addAttribute("municipio", mostrarOcupacionService.getMunicipiofromAreaNatural(idArea));
        model.addAttribute("mostrarOcupacionSvc", mostrarOcupacionService);
        model.addAttribute("servicios", getTiposServicioService.getServiciosArea(idArea));
        model.addAttribute("TipoServicioService", getTiposServicioService);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        FiltroOcupacion filtro = new FiltroOcupacion();
        filtro.setIdArea(idArea);
        model.addAttribute("filtro",filtro);
        return "areaNatural/consultarOcupacion";
    }
    @RequestMapping(value="/consultarOcupacion/{idArea}/{fecha}/{idFranjaHoraria}")
    public String consultarOcupacionFiltrado(Model model, @PathVariable int idArea, @PathVariable String fecha,@PathVariable int idFranjaHoraria,
                                             HttpSession session) {
        model.addAttribute("areaNatural", areaNaturalDao.getAreaNatural(idArea));
        model.addAttribute("municipio", mostrarOcupacionService.getMunicipiofromAreaNatural(idArea));
        model.addAttribute("mostrarOcupacionSvc", mostrarOcupacionService);
        model.addAttribute("servicios", getTiposServicioService.getServiciosArea(idArea));
        model.addAttribute("TipoServicioService", getTiposServicioService);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        model.addAttribute("dia",fecha);
        model.addAttribute("franja",idFranjaHoraria);
        FiltroOcupacion filtro;
        if(session.getAttribute("filtro")!=null)
            filtro = (FiltroOcupacion) session.getAttribute("filtro");
        else
            filtro = new FiltroOcupacion();
        filtro.setIdArea(idArea);
        model.addAttribute("filtro",filtro);
        return "areaNatural/consultarOcupacion";
    }
    @RequestMapping(value="/consultarOcupacion", method = RequestMethod.POST)
    public String consultarOcupacionPOST(@ModelAttribute FiltroOcupacion filtro, HttpSession session) {
        session.setAttribute("filtro",filtro);
        return "redirect:consultarOcupacion/"+filtro.getIdArea()+"/"+filtro.getFecha()+"/"+filtro.getIdFranjaHoraria();
    }


}
