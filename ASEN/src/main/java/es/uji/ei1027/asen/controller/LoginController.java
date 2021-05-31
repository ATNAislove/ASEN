package es.uji.ei1027.asen.controller;

import es.uji.ei1027.asen.dao.UserDao;
import es.uji.ei1027.asen.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Collection;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDetails.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDetails user = (UserDetails) o;
        if (user.getUsername().trim().equals(" ")) {
            errors.rejectValue("username", "obligatorio",
                    "Por favor, introduce un usuario");
        }
        if (user.getPassword().trim().equals(" ")) {
            errors.rejectValue("password", "obligatorio, " +
                    "Por favor, introduce una contraseña");
        }
    }
}


@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    /*@Override
    public UserDetails loadUserByUsername(String username, String password) {
        return null;
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return null;
    }*/
}
