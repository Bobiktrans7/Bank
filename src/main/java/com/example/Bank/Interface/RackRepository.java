package com.example.Bank.Interface;

import com.example.Bank.Entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RackRepository extends JpaRepository<Rack, Long> {}
