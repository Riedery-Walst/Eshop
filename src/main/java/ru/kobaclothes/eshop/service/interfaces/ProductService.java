package ru.kobaclothes.eshop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.kobaclothes.eshop.exception.ImageUploadException;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void createProduct();

    void updateProduct(ProductDTO productDTO, Long productId, String email, MultipartFile[] image) throws ImageUploadException;

    void deleteProduct(Long productId, String email);
}
