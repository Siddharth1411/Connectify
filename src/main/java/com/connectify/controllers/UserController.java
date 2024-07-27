package com.connectify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    //user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        System.out.println("this is user dashboard");
        return "user/dashboard";
    }

    //user profile
    @RequestMapping(value = "/profile")
    public String userProfile(){
        return "user/profile";
    }
}
