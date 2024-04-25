package com.example.Bank.Service;

import com.example.Bank.Entity.*;
import com.example.Bank.Interface.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final StuffRepository stuffRepository;
    private final CellRepository cellRepository;
    private final ShelfRepository shelfRepository;
    private final MarkRepository markRepository;

    public RestService(ClientRepository clientRepository, OrderRepository orderRepository, StuffRepository stuffRepository, CellRepository cellRepository, ShelfRepository shelfRepository, com.example.Bank.Interface.MarkRepository markRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.stuffRepository = stuffRepository;
        this.cellRepository = cellRepository;
        this.shelfRepository = shelfRepository;
        this.markRepository = markRepository;
    }

    public void updateClientIdTel(String telephone, String idTel) {
        Optional<Client> clientOptional = clientRepository.findByTelephone(telephone);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setIdTel(idTel);
            clientRepository.save(client);
        } else {

        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByIdTel(String idTel) {
        Optional<Client> clientOptional = clientRepository.findByIdTel(idTel);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return orderRepository.findByRecipientPhoneNumber(client.getTelephone());
        } else {

            return Collections.emptyList();
        }
    }


    public List<Cell> getAllCell() {
        return cellRepository.findAll();
    }

    public List<Shelf> getAllShell() {
        return shelfRepository.findAll();
    }


    public List<Stuff> getAllStuff() {
        return stuffRepository.findAll();
    }

    public List<Client> getAllUsers() {
        return clientRepository.findAll();
    }

    public List<Mark> getAllMark() {    return markRepository.findAll(); }


    public Stuff updateAssessment(Long id, Integer assessment) {
        Optional<Stuff> stuffOptional = stuffRepository.findById(id);
        if (stuffOptional.isPresent()) {
            Stuff stuff = stuffOptional.get();
            stuff.setAssessment(assessment);
            return stuffRepository.save(stuff);
        } else {

            return null;
        }
    }

    public void sendAssessmentProposal(String idTel) {
        Optional<Client> client = clientRepository.findByIdTel(idTel);
        if (client != null) {

        }
    }

    public List<Order> getOrdersWithExpiringStorage() {
        LocalDate currentDate = LocalDate.now(); // Текущая дата
        return orderRepository.findOrdersWithExpiringStorage(currentDate);
    }

    public void sendOrderNotification(Long orderId) {
        // Логика для получения клиента, связанного с заказом
        Client client = clientRepository.findByOrderId(orderId);

        if (client != null) {
            String idTel = client.getIdTel();
            String message = "Ваш заказ с номером " + orderId + " пришел";

            // Здесь вы можете реализовать логику для отправки уведомления
            // Например, отправить SMS или электронное письмо на номер телефона или адрес электронной почты из столбца idTel
            System.out.println("Отправлено уведомление на " + idTel + ": " + message);
        }
    }

    public Client updateComment(String idTel, String comment) {
        Optional<Client> clientOptional = clientRepository.findByIdTel(idTel);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setComment(comment);
            return clientRepository.save(client);
        }
        return null;
    }


    public void updateClientIdMark(Long id, Long id_wk, Long assessment) {
        Optional<Mark> clientOptional = markRepository.findById(id);
        if (clientOptional.isPresent()) {
            Mark mark = clientOptional.get();
            mark.setId_stuff(id_wk);
            mark.setAssessment(assessment);
            markRepository.save(mark);
        } else {

        }
    }



}
