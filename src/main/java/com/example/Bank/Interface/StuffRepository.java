package com.example.Bank.Interface;


import com.example.Bank.Entity.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuffRepository extends JpaRepository<Stuff, Long>{
    Stuff findByUsername(String name);
}
