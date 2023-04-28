package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products_statistics")
public class ProductStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "ProductStatistics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;

    @Column(name = "visitors_count")
    private Long  visitorsCount;

    @Column(name = "orders_count")
    private Long  ordersCount;
}
