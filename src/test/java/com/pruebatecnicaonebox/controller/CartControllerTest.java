package com.pruebatecnicaonebox.controller;

import com.pruebatecnicaonebox.dao.CartRepository;
import com.pruebatecnicaonebox.dao.ProductRepository;
import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.Product;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    private Cart exampleCart;

    private Product exampleProduct1;
    private Product exampleProduct2;


    @Test
    void getCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")).andDo(
                result -> {
                    mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/{id}", result.getResponse().getContentAsString()))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andExpect(content().json("{\"id\":\"" +  result.getResponse().getContentAsString() + "\",\"productList\":[]}"));
                }
        );
    }

    @Test
    void createCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("text/plain;charset=UTF-8")).andDo(
                        result -> {
                            String content = result.getResponse().getContentAsString();
                            System.out.println(content);
                            Assertions.assertTrue(content.length() == 36);
                            MatcherAssert.assertThat(content, Matchers.matchesRegex("^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}$"));
                        }
                );
    }

    @Test
    void addProductToCart() throws Exception {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        Product product = productRepository.save(
                Product.builder().amount(3.0).description("test").build()
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/{cartId}/product/{productId}", cart.getId(), product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"description\":\"test\",\"amount\":3.0}]"))
                .andExpect(status().isAccepted());

        cartRepository.findById(cart.getId()).ifPresent(
                testCart -> {
                    assertFalse(testCart.getProductList().isEmpty());
                    assertThat(testCart.getProductList().size()).isEqualTo(1);
                }
        );

        product = productRepository.save(
                Product.builder().amount(4.0).description("test").build()
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/{cartId}/product/{productId}", cart.getId(), product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"description\":\"test\",\"amount\":3.0},{\"description\":\"test\",\"amount\":4.0}]"))
                .andExpect(status().isAccepted());

        cartRepository.findById(cart.getId()).ifPresent(
                testCart -> {
                    assertFalse(testCart.getProductList().isEmpty());
                    assertThat(testCart.getProductList().size()).isEqualTo(2);
                }
        );

    }

    @Test
    void removeProductToCart() throws Exception {

        exampleProduct1= productRepository.save(
                Product.builder().amount(3.0).description("test").build()
        );

        exampleProduct2 = productRepository.save(
                Product.builder().amount(4.0).description("test2").build()
        );

        exampleCart = cartRepository.save(Cart.builder().productList(Arrays.asList(exampleProduct1,exampleProduct2)).build());


        Assertions.assertTrue(cartRepository.existsById(exampleCart.getId()));
        Assertions.assertTrue(productRepository.existsById(exampleProduct1.getId()));
        Assertions.assertTrue(productRepository.existsById(exampleProduct2.getId()));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{cartId}/product/{productId}", exampleCart.getId(), exampleProduct1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        cartRepository.findById(exampleCart.getId()).ifPresent(
                testCart -> {
                    assertFalse(testCart.getProductList().isEmpty());
                    assertThat(testCart.getProductList().size()).isEqualTo(1);
                }
        );
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{cartId}/product/{productId}", exampleCart.getId(), exampleProduct2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        cartRepository.findById(exampleCart.getId()).ifPresent(
                testCart -> {
                    assertTrue(testCart.getProductList().isEmpty());
                }
        );
    }

    @Test
    void deleteCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")).andDo(
                result -> {
                    String id=result.getResponse().getContentAsString();
                    mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{id}", id))
                            .andExpect(status().isAccepted()).andDo(
                                    response -> {
                                        assertFalse(cartRepository.existsById(UUID.fromString(id)));
                                    }
                            );
                }
        );
    }
}