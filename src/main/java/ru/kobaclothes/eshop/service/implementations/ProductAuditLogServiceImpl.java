package ru.kobaclothes.eshop.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.model.ProductAuditLog;
import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.repository.ProductAuditLogRepository;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;

import java.time.LocalDateTime;

@Service
public class ProductAuditLogServiceImpl implements ProductAuditLogService {
    private final ProductAuditLogRepository productAuditLogRepository;

    @Autowired
    public ProductAuditLogServiceImpl(ProductAuditLogRepository productAuditLogRepository) {
        this.productAuditLogRepository = productAuditLogRepository;
    }

    @Override
    public void logProductAction(String productName, String accountInfo, ProductStatus productStatus) {
        ProductAuditLog auditLog = new ProductAuditLog();
        auditLog.setProductName(productName);
        auditLog.setProductStatus(productStatus);
        auditLog.setAccountInfoFullName(accountInfo);
        auditLog.setUpdated(LocalDateTime.now());
        productAuditLogRepository.save(auditLog);
    }


}
