package ru.kobaclothes.eshop.dao;

import ru.kobaclothes.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
