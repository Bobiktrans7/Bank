package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Stuff")
@NoArgsConstructor
public class Stuff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "assessment")
    private Integer assessment;

    @Column(name = "comment")
    private String comment;


    public Stuff(String username, String password, Integer assessment) {
    }
}
