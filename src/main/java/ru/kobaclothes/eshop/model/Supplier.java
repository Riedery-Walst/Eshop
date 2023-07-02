package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "email")
    private String email;

    @Column(name = "product")
    private String product;

    @Column(name = "quantity")
    private String quantity;

    @Lob
    private byte[] contacts;

    @Column(name = "paid")
    private boolean paid;
}
