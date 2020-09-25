package com.twuc.shopping.controller;

import com.twuc.shopping.dto.ProductDto;
import com.twuc.shopping.entity.ProductEntity;
import com.twuc.shopping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void should_get_all_products() throws Exception {
        productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        productRepository.save(ProductEntity.builder()
                .name("可乐2")
                .price(2)
                .unit("罐")
                .image("image2")
                .build());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("可乐1")))
                .andExpect(jsonPath("$[0].price", is(1)))
                .andExpect(jsonPath("$[0].unit", is("瓶")))
                .andExpect(jsonPath("$[0].image", is("image1")))
                .andExpect(jsonPath("$[1].name", is("可乐2")))
                .andExpect(jsonPath("$[1].price", is(2)))
                .andExpect(jsonPath("$[1].unit", is("罐")))
                .andExpect(jsonPath("$[1].image", is("image2")));
    }

    @Test
    void should_create_product() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("name")
                .price(123)
                .unit("unit")
                .image("image")
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDto.toJson()))
                .andExpect(status().isCreated());

        ProductEntity productEntity = productRepository.findAll().get(0);
        assertEquals(productDto.getName(), productEntity.getName());
        assertEquals(productDto.getPrice(), productEntity.getPrice());
        assertEquals(productDto.getUnit(), productEntity.getUnit());
        assertEquals(productDto.getImage(), productEntity.getImage());
    }

    @Test
    void should_not_create_product_when_invalid_input() throws Exception {
        ProductDto productDtoWithoutName = ProductDto.builder()
                .name(null)
                .price(123)
                .unit("unit")
                .image("image")
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoWithoutName.toJson()))
                .andExpect(status().isBadRequest());

        ProductDto productDtoWithoutPrice = ProductDto.builder()
                .name("name")
                .price(null)
                .unit("unit")
                .image("image")
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoWithoutPrice.toJson()))
                .andExpect(status().isBadRequest());

        ProductDto productDtoWithoutUnit = ProductDto.builder()
                .name("name")
                .price(123)
                .unit(null)
                .image("image")
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoWithoutUnit.toJson()))
                .andExpect(status().isBadRequest());

        ProductDto productDtoWithoutImage = ProductDto.builder()
                .name("name")
                .price(123)
                .unit("unit")
                .image(null)
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoWithoutImage.toJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_create_product_when_name_already_exists() throws Exception {
        productRepository.save(ProductEntity.builder()
                .name("可乐")
                .build());

        ProductDto productDto = ProductDto.builder()
                .name("可乐")
                .price(123)
                .unit("unit")
                .image("image")
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDto.toJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("商品名称已存在，请输入新的商品名称")));
    }
}
