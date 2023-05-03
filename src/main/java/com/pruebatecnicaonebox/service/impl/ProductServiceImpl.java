package com.pruebatecnicaonebox.service.impl;

import com.pruebatecnicaonebox.dao.ProductRepository;
import com.pruebatecnicaonebox.mapper.MapperDto;
import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.dto.ProductDto;
import com.pruebatecnicaonebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private MapperDto mapperDto;

    @Override
    public ProductDto getProductById(UUID id) {
        return mapperDto.productToProductDto(productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with id " + id)
        ));
    }

    @Override
    public String createProduct(ProductDto productDto) {

        Product product = mapperDto.productDtoToProduct(productDto);

        return  productRepository.save(product).getId().toString();
    }

    @Override
    public String deleteProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with id " + id)
        );
        productRepository.delete(product);
        return product.getId().toString();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return mapperDto.productToProductDto(productRepository.findAll());
    }
}
