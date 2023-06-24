package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaclothes.eshop.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findProductCategoryByName(String category);
    ProductCategory findByNameAndParent(String name, ProductCategory parent);
    ProductCategory findByName(String name);
}
