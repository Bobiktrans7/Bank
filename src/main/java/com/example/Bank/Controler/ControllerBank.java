package com.example.Bank.Controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerBank {

    @GetMapping("/login")
    public String login() {
        return "login";
    }



}
