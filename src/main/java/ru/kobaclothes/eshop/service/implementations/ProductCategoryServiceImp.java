package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.exception.ProductCategoryAlreadyExistException;
import ru.kobaclothes.eshop.exception.ProductCategoryNotFoundException;
import ru.kobaclothes.eshop.model.ProductCategory;
import ru.kobaclothes.eshop.repository.ProductCategoryRepository;
import ru.kobaclothes.eshop.service.interfaces.ProductCategoryService;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImp(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory createCategory(String name) {
        ProductCategory existingCategory = productCategoryRepository.findProductCategoryByName(name);

        if (existingCategory != null) {
            throw new ProductCategoryAlreadyExistException("Category already exist: " + existingCategory);
        } else {
            ProductCategory newCategory = new ProductCategory();
            newCategory.setName(name);

            return productCategoryRepository.save(newCategory);
        }
    }

    public ProductCategory createChildCategory(String name, Long parentId) {
        ProductCategory parent = productCategoryRepository.findById(parentId)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Parent category not found"));

        ProductCategory existingChild = productCategoryRepository.findByNameAndParent(name, parent);
        if (existingChild != null) {
            throw new ProductCategoryAlreadyExistException("Child category with the same name already exists");
        }

        ProductCategory child = new ProductCategory();
        child.setName(name);
        child.setParent(parent);

        return productCategoryRepository.save(child);
    }

    @Override
    public ProductCategory getCategory(String categoryName) {
        ProductCategory category = productCategoryRepository.findByName(categoryName);
        if (category == null) {
            throw new ProductCategoryNotFoundException("Category not found");
        }

        if (!category.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cannot add products to non-leaf categories");
        }

        while (category.getParent() != null) {
            category = category.getParent();
        }

        return category;
    }

    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }

}
