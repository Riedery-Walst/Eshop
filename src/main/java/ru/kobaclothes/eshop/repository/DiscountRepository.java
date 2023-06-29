package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount getDiscountByProductId(Long productId);
}
