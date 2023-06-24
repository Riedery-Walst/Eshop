package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products_statistics")
public class ProductStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "orders_count")
    private Long ordersCount;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "vax")
    private BigDecimal vax;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "revenue")
    private BigDecimal revenue;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "total_vax")
    private BigDecimal totalVax;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "total_income")
    private BigDecimal totalIncome;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

}
