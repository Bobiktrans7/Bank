package com.example.Bank.Interface;

import com.example.Bank.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientIntarface extends JpaRepository<ClientIntarface, Long> {
    Client findByLogin(String login);
}
