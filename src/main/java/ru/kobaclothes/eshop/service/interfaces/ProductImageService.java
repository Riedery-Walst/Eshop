package ru.kobaclothes.eshop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.kobaclothes.eshop.exception.ImageUploadException;
import ru.kobaclothes.eshop.model.Product;

public interface ProductImageService {
    void uploadImages(Product product, MultipartFile[] files) throws ImageUploadException;
}
