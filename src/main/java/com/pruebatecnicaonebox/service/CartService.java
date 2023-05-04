package com.pruebatecnicaonebox.service;

import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.dto.CartDto;
import com.pruebatecnicaonebox.model.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public CartDto getCartById(UUID id) throws NotFoundException;

    public String createCart();

    public String deleteCartById(UUID id) throws NotFoundException;

    public CartDto addProductToCart(UUID id,UUID idProduct) throws NotFoundException;

    public CartDto removeProductToCart(UUID id,UUID idProduct) throws NotFoundException;

    public List<CartDto> getAllCarts();
}
