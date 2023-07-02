package ru.kobaclothes.eshop.service.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kobaclothes.eshop.exception.*;
import ru.kobaclothes.eshop.model.*;
import ru.kobaclothes.eshop.repository.ProductCategoryRepository;
import ru.kobaclothes.eshop.repository.ProductRepository;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.dto.ProductDTO;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;
import ru.kobaclothes.eshop.service.interfaces.ProductImageService;
import ru.kobaclothes.eshop.service.interfaces.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductAuditLogService productAuditLogService;
    private final UserRepository userRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductImageService productImageService;

    public ProductServiceImpl(ProductRepository productRepository, ProductAuditLogService productAuditLogService, UserRepository userRepository, ProductCategoryRepository productCategoryRepository, ProductImageService productImageService) {
        this.productRepository = productRepository;
        this.productAuditLogService = productAuditLogService;
        this.userRepository = userRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productImageService = productImageService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct() {
        Product product = new Product();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO, Long productId, String email, MultipartFile[] images) throws ImageUploadException {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        ProductCategory category = productCategoryRepository.findProductCategoryByName(productDTO.getCategory());
        if (category == null) {
            throw new ProductCategoryNotFoundException("Product category not found");
        }

        Product existingProduct = productRepository.findByName(productDTO.getName());
        if (existingProduct != null && !existingProduct.getId().equals(productId)) {
            throw new DuplicateProductException("Product with the same name already exists");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        AccountInfo accountInfo = user.getAccountInfo();
        if (accountInfo == null || accountInfo.getFirstName() == null || accountInfo.getLastName() == null) {
            throw new InvalidAccountInfoException("Full name not found");
        }
        productImageService.uploadImages(product, images);

        category.getProducts().add(product);
        product.setProductCategory(category);

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setComposition(productDTO.getComposition());

        if (product.getProductAuditLogs() == null) {
            productAuditLogService.logProductAction(product.getName(), accountInfo, ProductStatus.CREATED);
        } else {
            productAuditLogService.logProductAction(product.getName(), accountInfo, ProductStatus.UPDATED);
        }
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId, String email) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (user.getAccountInfo() != null && user.getAccountInfo().getFirstName() != null && user.getAccountInfo().getLastName() != null) {
            AccountInfo accountInfo = user.getAccountInfo();
            productAuditLogService.logProductAction(product.getName(), accountInfo, ProductStatus.DELETED);
            productRepository.delete(product);
        } else {
            throw new InvalidAccountInfoException("Full name not found");
        }
    }


}
