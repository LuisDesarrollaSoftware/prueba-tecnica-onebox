package com.pruebatecnicaonebox.mapper;

import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.dto.CartDto;
import com.pruebatecnicaonebox.model.dto.ProductDto;

import java.util.List;


public interface MapperDto {

    Cart cartDtoToCart(CartDto cartDto);

    CartDto cartToCartDto(Cart cart);

    List<Cart> cartDtoToCart(List<CartDto> cartDto);

    List<CartDto> cartToCartDto(List<Cart> cart);

    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product product);

    List<Product> productDtoToProduct(List<ProductDto> productDto);

    List<ProductDto> productToProductDto(List<Product> product);
}

