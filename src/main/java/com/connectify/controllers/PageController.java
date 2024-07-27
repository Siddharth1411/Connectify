package com.connectify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.connectify.forms.Userform;
import com.connectify.helper.Message;
import com.connectify.helper.MessageType;
import com.connectify.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
    @Autowired
    UserService userService;
    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        //sending data to view
        model.addAttribute("name", "Siddharth");
        model.addAttribute("age", "24");
        model.addAttribute("leetcodeProfile", "https://leetcode.com/u/Siddharth1411/");
        return "home"; //name of a page(view template file home.html)
    }
    
    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", true);
        return "about";
    }
    @RequestMapping("/services")
    public String servicesPage(){
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        return new String("contact");
    }
    
    @RequestMapping("/login")
    public String login() {
        return new String("login");
    }

    @RequestMapping("/register")
    public String register(Model model){
        Userform userform = new Userform();
        // userform.setPhoneNumber("9999999");
        model.addAttribute("Userform", userform);
        return "register";
    }

    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegistration(@Valid @ModelAttribute("Userform") Userform userform,BindingResult rBindingResult, HttpSession session) {
        //validate
        if(rBindingResult.hasErrors()){
            return "register";
        }
        //fetch user data from form => add to db
        userService.registerNewUser(userform);

        // remove the message
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/register";
    }
    
}
