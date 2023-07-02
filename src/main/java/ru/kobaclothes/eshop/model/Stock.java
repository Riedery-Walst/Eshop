package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private Long quantity;
}
