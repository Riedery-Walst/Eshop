package ru.kobaclothes.eshop.dao;

import ru.kobaclothes.eshop.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {
}
