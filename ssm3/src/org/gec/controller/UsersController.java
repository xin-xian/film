package org.gec.controller;

import org.gec.pojo.Users;
import org.gec.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
        Users users = usersService.Login(username,password);
        if(users != null){
            session.setAttribute("user_session",users);
            return "forward:/toCinema";
        }else{
            return "login";
        }
    }
}
