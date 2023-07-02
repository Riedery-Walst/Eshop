package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.ProductCategory;

public interface ProductCategoryService {

    ProductCategory getCategory(String categoryName);
}
