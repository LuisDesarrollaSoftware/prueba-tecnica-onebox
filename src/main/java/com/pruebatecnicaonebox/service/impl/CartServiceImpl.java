package com.pruebatecnicaonebox.service.impl;

import com.pruebatecnicaonebox.dao.CartRepository;
import com.pruebatecnicaonebox.dao.ProductRepository;
import com.pruebatecnicaonebox.mapper.MapperDto;
import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.Product;
import com.pruebatecnicaonebox.model.dto.CartDto;
import com.pruebatecnicaonebox.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class CartServiceImpl implements CartService {

    private static final long MAX_INACTIVE_TIME = 60000;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MapperDto mapperDto;

    @Override
    public CartDto getCartById(UUID id) {
        return mapperDto.cartToCartDto(cartRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cart not found with id " + id)
        ));
    }

    @Override
    public String createCart() {
        Cart cart = new Cart();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cart.setLastUpdate(timestamp);
        return cartRepository.save(cart).getId().toString();
    }

    @Override
    public String deleteCartById(UUID id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cart not found with id " + id)
        );
        cartRepository.delete(cart);
        return cart.getId().toString();
    }

    @Override
    public CartDto addProductToCart(UUID id, UUID idProduct) {

        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cart not found with id " + id)
        );

        Product product = productRepository.findById(idProduct).orElseThrow(
                () -> new RuntimeException("Product not found with id " + id)
        );

        cart.getProductList().add(product);
        cart.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        cartRepository.save(cart);

        return mapperDto.cartToCartDto(cart);
    }

    @Override
    public CartDto removeProductToCart(UUID id, UUID idProduct) {

        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cart not found with id " + id)
        );

        Product product = productRepository.findById(idProduct).orElseThrow(
                () -> new RuntimeException("Product not found with id " + id)
        );

        if(cart.getProductList().contains(product)){
            cart.getProductList().remove(cart.getProductList().lastIndexOf(product));
            cart.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            cartRepository.save(cart);
            return mapperDto.cartToCartDto(cart);
        }
        return null;
    }

    @Override
    public List<CartDto> getAllCarts() {
        return mapperDto.cartToCartDto(cartRepository.findAll());
    }

    @Scheduled(fixedRate = MAX_INACTIVE_TIME)
    public void deleteInactiveCarts() {
        long currentTime = System.currentTimeMillis();
        cartRepository.findAll().stream()
                .filter(cart -> currentTime - cart.getLastUpdate().getTime() > MAX_INACTIVE_TIME)
                .forEach(cart -> cartRepository.delete(cart));
    }
}
