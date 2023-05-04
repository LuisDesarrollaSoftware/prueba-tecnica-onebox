package com.pruebatecnicaonebox.controller;

import com.pruebatecnicaonebox.dao.ProductRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONStringer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void CreateAndGetProduct() throws Exception {
        String body = new JSONStringer().object().key("amount").value(3.0).key("description").value("test").endObject().toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(
                result -> {
                    mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}", result.getResponse().getContentAsString()))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andExpect(content().json(body));
                }
        );
    }

    @Test
    void testGetAllProduct() throws Exception {
        String body = new JSONStringer().object().key("amount").value(3.0).key("description").value("test").endObject().toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        String body2 = new JSONStringer().object().key("amount").value(3.0).key("description").value("test").endObject().toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .content(body2)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(
                        result -> {
                            Assertions.assertTrue(result.getResponse().getContentAsString().contains("\"description\":\"test\",\"amount\":3.0"));
                            Assertions.assertTrue(result.getResponse().getContentAsString().contains("\"description\":\"test\",\"amount\":3.0"));
                        }
                );
    }

    @Test
    void testDeleteProduct() throws Exception {

        String body = new JSONStringer().object().key("amount").value(3.0).key("description").value("test").endObject().toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(
                resultCreate -> {
                    Assertions.assertTrue(productRepository.existsById(UUID.fromString(resultCreate.getResponse().getContentAsString())));
                    mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/{id}", resultCreate.getResponse().getContentAsString()))
                            .andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=UTF-8"))
                            .andDo(
                                    resultDelete -> {
                                        String content = resultCreate.getResponse().getContentAsString();
                                        System.out.println(content);
                                        Assertions.assertTrue(content.length() == 36);
                                        MatcherAssert.assertThat(content, Matchers.matchesRegex("^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}$"));
                                        Assertions.assertFalse(productRepository.existsById(UUID.fromString(resultCreate.getResponse().getContentAsString())));
                                    }
                            );
                }
        );

    }
}