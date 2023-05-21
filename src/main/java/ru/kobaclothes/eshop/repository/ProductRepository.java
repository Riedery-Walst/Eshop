package ru.kobaclothes.eshop.repository;

import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
