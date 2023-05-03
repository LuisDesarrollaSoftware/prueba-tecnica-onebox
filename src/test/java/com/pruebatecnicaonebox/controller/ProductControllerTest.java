package com.pruebatecnicaonebox.controller;

import com.pruebatecnicaonebox.dao.CartRepository;
import com.pruebatecnicaonebox.dao.ProductRepository;
import com.pruebatecnicaonebox.model.Cart;
import com.pruebatecnicaonebox.model.Product;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

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
    void getProduct() throws Exception {
        exampleProduct1= Product.builder().amount(3.0).description("test").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product",exampleProduct1)).andDo(
                result -> {
                    mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}", result.getResponse().getContentAsString()))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                }
        );
    }

    @Test
    void testGetProduct() {
    }

    @Test
    void testCreateProduct() {
    }

    @Test
    void testDeleteProduct() {
    }
}