package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "telephone")
    private String telephone;

    @Column(name = "id_tel")
    private String idTel;

    @Column(name = "comment")
    private String comment;

    @Column(name = "order_id")
    private Long orderId;
}
