package com.example.Bank.Interface;

import com.example.Bank.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByTelephone(String telephone);
    Optional<Client> findByIdTel(String idTel);
    Client findByOrderId(Long orderId);

}
