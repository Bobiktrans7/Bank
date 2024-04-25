package com.example.Bank.Service;

import com.example.Bank.Entity.Order;
import com.example.Bank.Interface.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestService notificationService;

    public OrderService(OrderRepository orderRepository, RestService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void processNewOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        notificationService.sendOrderNotification(savedOrder.getId());
    }
}
