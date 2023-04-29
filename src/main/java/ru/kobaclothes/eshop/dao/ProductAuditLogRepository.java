package ru.kobaclothes.eshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.ProductAuditLog;

@Repository
public interface ProductAuditLogRepository extends JpaRepository<ProductAuditLog, Long> {
}
