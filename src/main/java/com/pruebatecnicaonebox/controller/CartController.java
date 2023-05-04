package com.pruebatecnicaonebox.controller;

import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.dto.CartDto;
import com.pruebatecnicaonebox.model.exceptions.NotFoundException;
import com.pruebatecnicaonebox.model.exceptions.OneBoxApplicationException;
import com.pruebatecnicaonebox.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getCart() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID id) throws OneBoxApplicationException {
        return ResponseEntity.ok(cartService.getCartById(id));
    }
    @PostMapping("")
    public ResponseEntity<String> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart());
    }

    @PutMapping("/{id}/product/{idProduct}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable UUID id,@PathVariable UUID idProduct) throws OneBoxApplicationException {
        return ResponseEntity.accepted().body(cartService.addProductToCart(id,idProduct));
    }
    @DeleteMapping("/{id}/product/{idProduct}")
    public ResponseEntity<CartDto> removeProductToCart(@PathVariable UUID id,@PathVariable UUID idProduct) throws OneBoxApplicationException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.removeProductToCart(id,idProduct));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String>  deleteCart(@PathVariable UUID id) throws OneBoxApplicationException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.deleteCartById(id));
    }

}
