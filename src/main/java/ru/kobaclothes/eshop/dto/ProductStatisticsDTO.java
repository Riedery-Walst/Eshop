package ru.kobaclothes.eshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductStatisticsDTO {
    private BigDecimal purchasePrice;
}
