package ru.kobaclothes.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "count")
    private BigDecimal count;
    
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "composition")
    private String composition;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ProductColor color;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToOne(mappedBy = "product")
    private ProductStatistics productStatistics;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAuditLog> productAuditLogs;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
}

