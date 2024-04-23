package com.example.Bank.Controler;

import com.example.Bank.Entity.Stuff;
import com.example.Bank.Interface.StuffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final StuffService clientService;

    public RegistrationController(StuffService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Stuff());
        return "register";
    }

    @PostMapping("/register")
    public String registerClient(Stuff client) {
        clientService.registerStuff(client);
        return "redirect:/login";
    }

}
