package com.example.Bank.Controler;

import com.example.Bank.Entity.Order;
import com.example.Bank.Service.RestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class RestControllerPhone {
    private final RestService orderService;

    public RestControllerPhone(RestService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/by-id-tel")
    public List<Order> getOrdersByIdTel(@RequestParam String idTel) {
        return orderService.getOrdersByIdTel(idTel);
    }
//    http://localhost:8080/orders
}
