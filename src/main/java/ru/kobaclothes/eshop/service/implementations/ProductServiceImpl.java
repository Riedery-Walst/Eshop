package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.repository.ProductRepository;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;
import ru.kobaclothes.eshop.service.interfaces.ProductService;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductAuditLogService productAuditLogService;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, ProductAuditLogService productAuditLogService, UserService userService) {
        this.productRepository = productRepository;
        this.productAuditLogService = productAuditLogService;
        this.userService = userService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(Product product) {
        User currentUser = userService.getCurrentUserName();
        productRepository.save(product);
        productAuditLogService.logProductAction(product, ProductStatus.created, currentUser);
    }

    @Override
    public void updateProduct(Product product) {
        User currentUser = userService.getCurrentUserName();
        productRepository.save(product);
        productAuditLogService.logProductAction(product, ProductStatus.updated, currentUser);
    }

    @Override
    public void deleteProduct(Product product) {
        User currentUser = userService.getCurrentUserName();
        productRepository.delete(product);
        productAuditLogService.logProductAction(product, ProductStatus.deleted, currentUser);
    }
}
