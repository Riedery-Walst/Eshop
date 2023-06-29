package ru.kobaclothes.eshop.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.ProductStatistics;
import ru.kobaclothes.eshop.repository.ProductRepository;
import ru.kobaclothes.eshop.repository.ProductStatisticsRepository;
import ru.kobaclothes.eshop.dto.ProductStatisticsDTO;
import ru.kobaclothes.eshop.service.interfaces.ProductStatisticsService;


@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService {
    private final ProductStatisticsRepository productStatisticsRepository;
    private final ProductRepository productRepository;

    public ProductStatisticsServiceImpl(ProductStatisticsRepository productStatisticsRepository, ProductRepository productRepository) {
        this.productStatisticsRepository = productStatisticsRepository;
        this.productRepository = productRepository;
    }

    public void createProductStatistics(ProductStatisticsDTO productStatisticsDTO, String productName) throws JsonProcessingException {
        Product product = productRepository.findByName(productName);
        ProductStatistics existingStats = productStatisticsRepository.findByProduct(product);

        ProductStatistics productStatistics;

        if (existingStats != null) {
            productStatistics = existingStats;
        } else {
            productStatistics = new ProductStatistics();
            productStatistics.setProduct(product);
        }

        productStatistics.setPurchasePrice(productStatisticsDTO.getPurchasePrice());

        productStatisticsRepository.save(productStatistics);
    }

}
