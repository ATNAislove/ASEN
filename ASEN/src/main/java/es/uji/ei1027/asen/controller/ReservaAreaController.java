package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.model.AreaNatural;
import es.uji.ei1027.asen.model.ReservaArea;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservaArea")
public class ReservaAreaController {
    private ReservaArea reservaArea;

    //Dao(?)

    @RequestMapping(value="/add")
    public String addReservaArea(Model model) {
        model.addAttribute("reservaArea", new ReservaArea());
        return "reservaArea/add";
    }
}
