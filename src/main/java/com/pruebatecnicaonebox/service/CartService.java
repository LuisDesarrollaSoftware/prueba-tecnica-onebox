package com.pruebatecnicaonebox.service;

import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.dto.CartDto;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public CartDto getCartById(UUID id);

    public String createCart();

    public String deleteCartById(UUID id);

    public CartDto addProductToCart(UUID id,UUID idProduct);

    public CartDto removeProductToCart(UUID id,UUID idProduct);

    public List<CartDto> getAllCarts();
}
