package es.uji.ei1027.asen.controller;


import es.uji.ei1027.asen.dao.AccesibilidadDao;
import es.uji.ei1027.asen.dao.MunicipioDao;
import es.uji.ei1027.asen.model.Accesibilidad;
import es.uji.ei1027.asen.model.FiltroOcupacion;
import es.uji.ei1027.asen.model.UserDetails;
import es.uji.ei1027.asen.svc.GetAreasNaturalesService;
import es.uji.ei1027.asen.svc.GetFranjasHorariasService;
import es.uji.ei1027.asen.svc.GetMunicipiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/accesibilidad")
public class AccesibilidadController {

    private AccesibilidadDao accesibilidadDao;

    private GetFranjasHorariasService getFranjasHorariasService;
    private GetAreasNaturalesService getAreasNaturalesService;
    private GetMunicipiosService getMunicipiosService;
    @Autowired
    public void setGetMunicipiosService(GetMunicipiosService getMunicipiosService){
        this.getMunicipiosService=getMunicipiosService;
    }
    @Autowired
    public void setGetAreasNaturalesService(GetAreasNaturalesService getAreasNaturalesService){
        this.getAreasNaturalesService=getAreasNaturalesService;
    }
    @Autowired
    public void setGetFranjasHorariasService(GetFranjasHorariasService getFranjasHorariasService){
        this.getFranjasHorariasService=getFranjasHorariasService;
    }

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
    @RequestMapping("/verHorarios/{idArea}")
    public String listHorarios(Model model, @PathVariable int idArea,HttpSession session){
        model.addAttribute("accesibilidades", accesibilidadDao.getAccesibilidadesArea(idArea));
        model.addAttribute("area", idArea);
        model.addAttribute("getAreasNaturalesService",getAreasNaturalesService);
        model.addAttribute("franjaHorariaService", getFranjasHorariasService);
        session.setAttribute("filtro",null);
        return "accesibilidad/verHorarios";
    }


    @RequestMapping(value = "/add/{idArea}")
    public String addFranjaHoraria(Model model,@PathVariable int idArea ,HttpSession session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        UserDetails user = (UserDetails) session.getAttribute("user");
        int municipio = getMunicipiosService.getMunicipioGestor(user.getUsername());
        Accesibilidad accesibilidad = new Accesibilidad();
        accesibilidad.setIdArea(idArea);
        FiltroOcupacion filtro = (FiltroOcupacion) session.getAttribute("filtro");
        if(filtro!=null){
            filtro.setIdArea(idArea);
            accesibilidad.setFechaInicio(filtro.getFecha());
            accesibilidad.setFechaFin(filtro.getFechaFin());
            model.addAttribute("filtro",filtro);
            if(filtro.getFechaFin()==null){
                model.addAttribute("franjasHorarias",getFranjasHorariasService.getFranjasAccesible(idArea,filtro.getFecha()));
            }else{
                model.addAttribute("franjasHorarias",getFranjasHorariasService.getFranjasAccesible(idArea,filtro.getFecha(),filtro.getFechaFin()));
            }

        }else{
            FiltroOcupacion filtro2 = new FiltroOcupacion();
            filtro2.setIdArea(idArea);
            model.addAttribute("filtro",filtro2);
            model.addAttribute("franjasHorarias",getFranjasHorariasService.getFranjasServicio());
        }
        model.addAttribute("accesibilidad", accesibilidad);
        model.addAttribute("getAreasNaturalesService",getAreasNaturalesService);
        model.addAttribute("current_date", LocalDate.now().format(formatter));
        return "accesibilidad/add";
    }
    @RequestMapping(value="/filtrarDias", method = { RequestMethod.GET, RequestMethod.POST })
    public String filtrarDias(@ModelAttribute("filtro") FiltroOcupacion filtro,BindingResult bindingResult,HttpSession session){
        session.setAttribute("filtro",filtro);
        return "redirect:add/"+filtro.getIdArea();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("accesibilidad") Accesibilidad accesibilidad,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "accesibilidad/add";
        accesibilidadDao.addAccesibilidad(accesibilidad);
        return "redirect:verHorarios/"+accesibilidad.getIdArea();
    }

    @RequestMapping(value = "/update/{idFranjaHoraria}/{idArea}/{fechaInicio}", method = RequestMethod.GET)
    public String editAccesibilidad(Model model, @PathVariable int idFranjaHoraria, @PathVariable int idArea, @PathVariable String fechaInicio) {
        model.addAttribute("nombreArea",getAreasNaturalesService.getArea(idArea).getNombreArea());
        model.addAttribute("horasFranja",getFranjasHorariasService.getHorasFranja(idFranjaHoraria));
        model.addAttribute("accesibilidad", accesibilidadDao.getAccesibilidad(idFranjaHoraria,idArea,fechaInicio));
        return "accesibilidad/update";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("accesibilidad") Accesibilidad accesibilidad,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "accesibilidad/update";
        accesibilidadDao.updateAccesibilidad(accesibilidad);
        return "redirect:verHorarios/"+accesibilidad.getIdArea();
    }

    @RequestMapping(value = "/deleteHorario/{idFranjaHoraria}/{idArea}/{fechaInicio}")
    public String processDelete(@PathVariable int idFranjaHoraria,@PathVariable int idArea,@PathVariable String fechaInicio) {
        accesibilidadDao.deleteAccesibilidad(idFranjaHoraria,idArea,fechaInicio);
        return "redirect:../../../verHorarios/"+idArea;
    }
}
