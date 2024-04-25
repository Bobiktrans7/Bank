package com.example.Bank.Controler;

import com.example.Bank.Entity.Client;
import com.example.Bank.Service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final RestService clientService;

    public ClientController(RestService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/send-assessment/{idTel}")
    public ResponseEntity<String> sendAssessmentProposal(@PathVariable String idTel) {
        clientService.sendAssessmentProposal(idTel);
        return ResponseEntity.ok("Предложение оценить работу stuff было отправлено");
    }

//    http://localhost:8080/api/clients/send-assessment/23

    @PutMapping("/{idTel}/comment")
    public ResponseEntity<Client> updateComment(@PathVariable String idTel, @RequestBody String comment) {
        Client updatedClient = clientService.updateComment(idTel, comment);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
