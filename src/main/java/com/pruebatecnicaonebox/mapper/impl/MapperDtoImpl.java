package com.pruebatecnicaonebox.mapper.impl;

import com.pruebatecnicaonebox.mapper.MapperDto;
import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.dto.CartDto;
import com.pruebatecnicaonebox.model.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperDtoImpl implements MapperDto {
    @Override
    public Cart cartDtoToCart(CartDto cartDto) {

        return Cart.builder()
                .id(cartDto.getId())
                .productList(productDtoToProduct(cartDto.getProductList()))
                .lastUpdate(cartDto.getLastUpdate())
                .build();
    }

    @Override
    public CartDto cartToCartDto(Cart cart) {
        return  CartDto.builder()
                .id(cart.getId())
                .productList(productToProductDto(cart.getProductList()))
                .lastUpdate(cart.getLastUpdate())
                .build();
    }

    @Override
    public List<Cart> cartDtoToCart(List<CartDto> cartDto) {
        return cartDto.stream().map(this::cartDtoToCart).toList();
    }

    @Override
    public List<CartDto> cartToCartDto(List<Cart> cart) {
        return cart.stream().map(this::cartToCartDto).toList();
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .description(productDto.getDescription())
                .amount(productDto.getAmount())
                .build();
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        return ProductDto.builder().id(product.getId())
                .description(product.getDescription())
                .amount(product.getAmount())
                .build();
    }

    @Override
    public List<Product> productDtoToProduct(List<ProductDto> productDto) {
        return productDto.stream().map(this::productDtoToProduct).toList();
    }

    @Override
    public List<ProductDto> productToProductDto(List<Product> product) {
        return product.stream().map(this::productToProductDto).toList();
    }
}
