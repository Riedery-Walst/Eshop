package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.exception.UserNotFoundException;
import ru.kobaclothes.eshop.model.AccountInfo;
import ru.kobaclothes.eshop.model.ProductAuditLog;
import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.repository.ProductAuditLogRepository;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;

import java.time.LocalDateTime;

@Service
public class ProductAuditLogServiceImpl implements ProductAuditLogService {
    private final ProductAuditLogRepository productAuditLogRepository;
    private final UserRepository userRepository;

    public ProductAuditLogServiceImpl(ProductAuditLogRepository productAuditLogRepository, UserRepository userRepository) {
        this.productAuditLogRepository = productAuditLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void logProductAction(String productName,
                                 AccountInfo accountInfo,
                                 ProductStatus productStatus) {
        User user = userRepository.getUserByAccountInfo(accountInfo);
        if (user == null) {
            throw new UserNotFoundException("User not found for account info: " + accountInfo);
        }
        String fullName = user.getAccountInfo().getFirstName()
                + " " + user.getAccountInfo().getLastName();
        ProductAuditLog auditLog = productAuditLogRepository.findByName(productName);

        if (auditLog == null) {
            // Create a new audit log
            auditLog = new ProductAuditLog();
            auditLog.setProductName(productName);
            auditLog.setProductStatus(productStatus);
            auditLog.setAccountInfoFullName(fullName);
            auditLog.setUpdated(LocalDateTime.now());
        } else {
            // Update the existing audit log
            auditLog.setProductStatus(productStatus);
            auditLog.setAccountInfoFullName(fullName);
            auditLog.setUpdated(LocalDateTime.now());
        }

        productAuditLogRepository.save(auditLog);
    }

}
