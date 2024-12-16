package com.wsda.project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerLogin {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
