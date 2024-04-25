package com.example.Bank.Controler;

import com.example.Bank.Service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class RestControllers {
    private final RestService clientService;

    public RestControllers(RestService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/update-id-tel")
    public ResponseEntity<String> updateClientIdTel(@RequestBody ClientIdTelRequest request) {
        String telephone = request.getTelephone();
        String idTel = request.getIdTel();

        clientService.updateClientIdTel(telephone, idTel);

        return ResponseEntity.ok("Client ID_TEL updated successfully");
    }

//    @PostMapping("/update-mark")
//    public ResponseEntity<Long> updateClientIdMark(@RequestBody Cl request) {
//        String telephone = request.getTelephone();
//        String idTel = request.getIdTel();
//
//        clientService.updateClientIdTel(telephone, idTel);
//
//        return ResponseEntity.ok("Client ID_TEL updated successfully");
//    }




    static class ClientIdTelRequest {
        private String telephone;
        private String idTel;

        public String getTelephone() {
            return telephone;
        }

        public String getIdTel() {
            return idTel;
        }

    }

    static class MarkWork {
        private Long id;


    }

//    {
//        "telephone": "1234567890",
//            "idTel": "abc123"
//    }
}
