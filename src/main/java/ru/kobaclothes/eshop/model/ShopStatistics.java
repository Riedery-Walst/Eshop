package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "shop_statistics")
public class ShopStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_orders_count")
    private BigDecimal totalOrdersCount;

    @Column(name = "cost")
    private BigDecimal cost;

    //value-added tax
    @Column(name = "total_vax")
    private BigDecimal vax;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "total_income")
    private BigDecimal totalIncome;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;
}
