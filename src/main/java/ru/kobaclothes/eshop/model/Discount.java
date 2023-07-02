package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private BigDecimal discountValue;
}
