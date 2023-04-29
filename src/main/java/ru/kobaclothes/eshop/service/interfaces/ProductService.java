package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);
}
