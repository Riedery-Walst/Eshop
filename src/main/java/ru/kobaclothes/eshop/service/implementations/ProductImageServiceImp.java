package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kobaclothes.eshop.exception.ImageUploadException;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductImage;
import ru.kobaclothes.eshop.repository.ProductImageRepository;
import ru.kobaclothes.eshop.service.interfaces.ProductImageService;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProductImageServiceImp extends ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImp(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public void uploadImages(Product product, MultipartFile[] files) throws ImageUploadException {
        for (MultipartFile file : files) {
            ProductImage productImage = new ProductImage();

            try {
                productImage.setData(file.getBytes());
            } catch (IOException e) {
                throw new ImageUploadException("Error occurred during image upload", e);
            }

            productImage.setProduct(product);
            product.getProductImages().add(productImage);
        }

    }

    public byte[] getImageData(int productId) {
        Optional<ProductImage> productImage = productImageRepository.findByProductId(productId);
        return productImage.map(ProductImage::getData).orElse(null);
    }
}
