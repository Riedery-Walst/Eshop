package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_audit_logs")
public class ProductAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_info_full_name")
    private String accountInfoFullName;

    @Column(name = "product_name")
    private String productName;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}
