package ru.kobaclothes.eshop.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dao.ProductAuditLogRepository;
import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductAuditLog;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;

import java.util.Date;

@Service
public class ProductAuditLogServiceImpl implements ProductAuditLogService {
    private final ProductAuditLogRepository productAuditLogRepository;

    @Autowired
    public ProductAuditLogServiceImpl(ProductAuditLogRepository productAuditLogRepository) {
        this.productAuditLogRepository = productAuditLogRepository;
    }

    @Override
    public void logProductAction(Product product, ProductStatus productStatus, User currentUser) {
        ProductAuditLog auditLog = new ProductAuditLog();
        auditLog.setProduct(product);
        auditLog.setProductStatus(productStatus);
        auditLog.setUser(currentUser);
        auditLog.setTimestamp(new Date());
        productAuditLogRepository.save(auditLog);
    }
}
