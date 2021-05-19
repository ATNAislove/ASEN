package es.uji.ei1027.asen.controller;


import es.uji.ei1027.asen.dao.UserDao;
import es.uji.ei1027.asen.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/list")
    public String listUsers(HttpSession session, Model model) {
        String nextUrl = "user/list";
        session.setAttribute("nextUrl",nextUrl);
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("users", userDao.listAllUsers());
        return "user/list";
    }
}
