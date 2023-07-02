package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.model.CartItem;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.repository.CartItemRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImp {
    private final CartItemRepository cartItemRepository;

    public CartServiceImp(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem addToCart(Long userId, Product product, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.getItems().add(product);
        cartItem.setQuantity(quantity);
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        cartItem.setTotalPrice(totalPrice);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findAll(userId);
    }
}
