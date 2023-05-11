package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.User;

public interface ProductAuditLogService {
    void logProductAction(Product product, ProductStatus productStatus, User currentUser);
}
