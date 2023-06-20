package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.exception.DuplicateProductException;
import ru.kobaclothes.eshop.exception.InvalidAccountInfoException;
import ru.kobaclothes.eshop.exception.ProductNotFoundException;
import ru.kobaclothes.eshop.exception.UserNotFoundException;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductStatus;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.repository.ProductRepository;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.request.ProductRequest;
import ru.kobaclothes.eshop.service.interfaces.ProductAuditLogService;
import ru.kobaclothes.eshop.service.interfaces.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductAuditLogService productAuditLogService;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductAuditLogService productAuditLogService, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.productAuditLogService = productAuditLogService;
        this.userRepository = userRepository;
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
    public void updateProduct(ProductRequest productRequest, Long productId, String email) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        Product existingProduct = productRepository.findByName(productRequest.getName());
        if (existingProduct != null && !existingProduct.getId().equals(productId)) {
            throw new DuplicateProductException("Product with the same name already exists");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (user.getAccountInfo() != null && user.getAccountInfo().getFirstName() != null && user.getAccountInfo().getLastName() != null ) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCount(productRequest.getCount());
        product.setPrice(productRequest.getPrice());
        product.setComposition(productRequest.getComposition());

        productAuditLogService.logProductAction(product.getName(),
                (user.getAccountInfo().getFirstName() + " " + user.getAccountInfo().getLastName()),
                ProductStatus.UPDATED);
        productRepository.save(product);
        } else {
           throw new InvalidAccountInfoException("Full name not found");
        }
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
            productAuditLogService.logProductAction(product.getName(),
                    (user.getAccountInfo().getFirstName() + " " + user.getAccountInfo().getLastName()),
                    ProductStatus.UPDATED);
            productRepository.delete(product);
        } else {
            throw new InvalidAccountInfoException("Full name not found");
        }
    }
}
