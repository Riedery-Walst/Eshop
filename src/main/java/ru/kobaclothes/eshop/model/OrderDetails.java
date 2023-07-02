package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private BigDecimal price;
}
