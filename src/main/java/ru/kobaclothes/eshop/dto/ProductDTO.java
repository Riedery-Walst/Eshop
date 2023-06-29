package ru.kobaclothes.eshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String composition;
    private String category;
    private String CategoryParentName;
    private String CategoryName;
}
