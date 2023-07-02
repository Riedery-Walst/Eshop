package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long productId);
    Product findByName(String name);
}
