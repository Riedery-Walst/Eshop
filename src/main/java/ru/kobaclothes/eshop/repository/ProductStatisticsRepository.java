package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductStatistics;

public interface ProductStatisticsRepository extends JpaRepository<ProductStatistics, Long> {
    ProductStatistics findByProduct(Product product);
}
