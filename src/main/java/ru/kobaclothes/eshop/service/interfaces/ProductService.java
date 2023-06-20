package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void createProduct();

    void updateProduct(ProductRequest productRequest, Long productId, String email);

    void deleteProduct(Long productId, String email);
}
