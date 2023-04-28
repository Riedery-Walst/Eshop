package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;

@Entity
public class HomeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "data")
    private byte[] data;
}
