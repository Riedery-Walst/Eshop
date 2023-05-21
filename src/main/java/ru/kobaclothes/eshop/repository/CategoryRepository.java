package ru.kobaclothes.eshop.repository;

import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {
}
