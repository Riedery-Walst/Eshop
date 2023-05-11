package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "product_audit_logs")
public class ProductAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}
