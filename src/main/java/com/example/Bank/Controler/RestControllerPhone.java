package com.example.Bank.Controler;

import com.example.Bank.Entity.Order;
import com.example.Bank.Service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class RestControllerPhone {
    private final RestService orderService;

    public RestControllerPhone(RestService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/by-id-tel")
    public List<Order> getOrdersByIdTel(@RequestParam String idTel) {
        return orderService.getOrdersByIdTel(idTel);
    }
//    http://localhost:8080/orders
}
