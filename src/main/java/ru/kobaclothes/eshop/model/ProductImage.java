package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
