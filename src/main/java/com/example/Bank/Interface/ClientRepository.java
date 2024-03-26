package com.example.Bank.Interface;

import com.example.Bank.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT u FROM Client u WHERE u.username = :username")
    Client findByUsername(@Param("username") String username);
}
