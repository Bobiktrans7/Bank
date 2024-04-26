package com.example.Bank.Controler;


import com.example.Bank.Entity.Order;
import com.example.Bank.Interface.OrderRepository;
import com.example.Bank.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class RestControllerOrder {
    private final RestService orderService;
    @Autowired
    private OrderRepository orderRepository;

    public RestControllerOrder(RestService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    //    http://localhost:8080/orders/by-id-tel?idTel=abc123

    @GetMapping("/expiring-storage")
    public ResponseEntity<List<Order>> getOrdersWithExpiringStorage() {
        List<Order> orders = orderService.getOrdersWithExpiringStorage();
        return ResponseEntity.ok(orders);
    }
//    http://localhost:8080/api/orders/expiring-storage


}
