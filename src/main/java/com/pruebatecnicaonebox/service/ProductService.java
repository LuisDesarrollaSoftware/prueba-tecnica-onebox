package com.pruebatecnicaonebox.service;

import com.pruebatecnicaonebox.model.dto.ProductDto;
import com.pruebatecnicaonebox.model.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto getProductById(UUID id) throws NotFoundException;

    String createProduct(ProductDto productDto);

    String deleteProductById(UUID id) throws NotFoundException;

    List<ProductDto> getAllProducts();
}
