package ru.kobaclothes.eshop.mapper;

import org.modelmapper.ModelMapper;
import ru.kobaclothes.eshop.dto.ProductDTO;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.Product;
import ru.kobaclothes.eshop.model.User;

public class ProductMapper {
    private ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User mapToUser(ProductDTO productDTO) {
        return modelMapper.map(productDTO, User.class);
    }

    public UserDTO mapToUserDTO(Product product) {
        return modelMapper.map(product, UserDTO.class);
    }
}