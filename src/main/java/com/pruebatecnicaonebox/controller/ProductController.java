package com.pruebatecnicaonebox.controller;

import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.dto.ProductDto;
import com.pruebatecnicaonebox.model.exceptions.NotFoundException;
import com.pruebatecnicaonebox.model.exceptions.OneBoxApplicationException;
import com.pruebatecnicaonebox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID id) throws OneBoxApplicationException {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping("")
    public ResponseEntity<String> createProduct( @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteProduct(@PathVariable UUID id) throws OneBoxApplicationException {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

}
