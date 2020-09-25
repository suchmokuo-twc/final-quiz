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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void should_get_orders() throws Exception {
        ProductEntity productEntity = productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        orderRepository.save(OrderEntity.builder()
                .amount(2)
                .product(productEntity)
                .build());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount", is(2)))
                .andExpect(jsonPath("$[0].product.name", is(productEntity.getName())))
                .andExpect(jsonPath("$[0].product.price", is(productEntity.getPrice())))
                .andExpect(jsonPath("$[0].product.unit", is(productEntity.getUnit())))
                .andExpect(jsonPath("$[0].product.image", is(productEntity.getImage())));
    }
}
