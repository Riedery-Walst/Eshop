package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaclothes.eshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
