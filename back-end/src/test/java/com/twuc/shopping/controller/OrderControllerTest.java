package com.twuc.shopping.controller;

import com.twuc.shopping.dto.OrderRequestDto;
import com.twuc.shopping.dto.ProductDto;
import com.twuc.shopping.entity.OrderEntity;
import com.twuc.shopping.entity.ProductEntity;
import com.twuc.shopping.repository.OrderRepository;
import com.twuc.shopping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void should_order_success() throws Exception {
        ProductEntity productEntity = productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        OrderRequestDto orderDto = OrderRequestDto.builder()
                .productId(productEntity.getId())
                .build();

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderDto.toJson()))
                .andExpect(status().isCreated());

        OrderEntity orderEntity = orderRepository.findAll().get(0);

        assertEquals(1, orderEntity.getAmount());
        assertEquals(orderDto.getProductId(), orderEntity.getProduct().getId());
    }
}
