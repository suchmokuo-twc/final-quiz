package com.twuc.shopping.service;

import com.twuc.shopping.dto.ProductDto;
import com.twuc.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}
