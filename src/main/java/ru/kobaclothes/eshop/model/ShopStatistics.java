package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shop_statistics")
public class ShopStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_orders_count")
    private int totalOrdersCount;

    @Column(name = "toral_revenue")
    private BigDecimal totalRevenue;
}
