package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.ProductStatus;

public interface ProductAuditLogService {
    void logProductAction(String productName, String accountInfo, ProductStatus productStatus);
}
