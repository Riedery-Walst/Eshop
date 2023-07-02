package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.repository.OrderRepository;
import ru.kobaclothes.eshop.repository.ProductRepository;
import ru.kobaclothes.eshop.service.interfaces.OrderService;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImp(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    /*public void placeOrder(Order order) {
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            Product product = orderDetails.getProduct();
            BigDecimal orderedAmount = orderDetails.getAmount();
            BigDecimal availableQuantity = product.getQuantity();

            if (availableQuantity.compareTo(orderedAmount) < 0) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
        }

        for (OrderDetails orderDetails : order.getOrderDetails()) {
            Product product = orderDetails.getProduct();
            BigDecimal orderedAmount = orderDetails.getAmount();
            BigDecimal availableQuantity = product.getQuantity();

            BigDecimal updatedQuantity = availableQuantity.subtract(orderedAmount);
            product.setQuantity(updatedQuantity);
        }

        orderRepository.save(order);

    }*/
}
