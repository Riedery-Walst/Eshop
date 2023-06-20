package ru.kobaclothes.eshop.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private int count;
    private BigDecimal price;
    private String composition;
}
