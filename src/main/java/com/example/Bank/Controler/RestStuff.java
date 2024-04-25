package com.example.Bank.Controler;

import com.example.Bank.Entity.Stuff;
import com.example.Bank.Service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuff")
public class RestStuff {
    private final  RestService restService;

    public RestStuff(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/all-stuff")
    public List<Stuff> getAllStuff() {
        return restService.getAllStuff();
    }


    @PostMapping("/{id}/assessment")
    public Stuff updateAssessment(@PathVariable Long id, @RequestBody Integer assessment) {
        return restService.updateAssessment(id, assessment);
    }
//    http://localhost:8080/stuff/2/assessment
    
}
