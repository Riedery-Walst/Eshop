package ru.kobaclothes.eshop.dao;

import ru.kobaclothes.eshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
