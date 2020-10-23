package com.twuc.shopping.controller;

import com.twuc.shopping.dto.OrderDto;
import com.twuc.shopping.dto.ProductDto;
import com.twuc.shopping.entity.OrderEntity;
import com.twuc.shopping.entity.OrderProductsEntity;
import com.twuc.shopping.entity.ProductEntity;
import com.twuc.shopping.repository.OrderProductsRepository;
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

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Autowired
    OrderProductsRepository orderProductsRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        orderRepository.deleteAll();
        orderProductsRepository.deleteAll();
    }

    @Test
    void should_order_success() throws Exception {
        ProductEntity productEntity = productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        OrderDto orderDto = OrderDto.builder()
                .products(Collections.singletonList(OrderDto.OrderProductDto.builder()
                        .amount(5)
                        .product(ProductDto.from(productEntity))
                        .build()))
                .build();

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderDto.toJson()))
                .andExpect(status().isCreated());

        OrderProductsEntity orderProductsEntity = orderProductsRepository.findAll().get(0);

        assertEquals(5, orderProductsEntity.getProductAmount());
        assertEquals(productEntity.getId(), orderProductsEntity.getProductId());
        assertNotNull(orderProductsEntity.getOrderId());
    }

    @Test
    void should_get_orders() throws Exception {
        ProductEntity productEntity1 = productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        ProductEntity productEntity2 = productRepository.save(ProductEntity.builder()
                .name("可乐2")
                .price(1)
                .unit("瓶")
                .image("image2")
                .build());

        String orderId = orderRepository.save(OrderEntity.builder().build()).getId();

        orderProductsRepository.save(OrderProductsEntity.builder()
                .orderId(orderId)
                .productId(productEntity1.getId())
                .productAmount(1)
                .build());

        orderProductsRepository.save(OrderProductsEntity.builder()
                .orderId(orderId)
                .productId(productEntity2.getId())
                .productAmount(2)
                .build());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(orderId)))
                .andExpect(jsonPath("$[0].products", hasSize(2)))
                .andExpect(jsonPath("$[0].products[0].product.id", is(productEntity1.getId())))
                .andExpect(jsonPath("$[0].products[0].amount", is(1)))
                .andExpect(jsonPath("$[0].products[1].product.id", is(productEntity2.getId())))
                .andExpect(jsonPath("$[0].products[1].amount", is(2)));
    }

    @Test
    void should_delete_order() throws Exception {
        ProductEntity productEntity1 = productRepository.save(ProductEntity.builder()
                .name("可乐1")
                .price(1)
                .unit("瓶")
                .image("image1")
                .build());

        ProductEntity productEntity2 = productRepository.save(ProductEntity.builder()
                .name("可乐2")
                .price(1)
                .unit("瓶")
                .image("image2")
                .build());

        String orderId = orderRepository.save(OrderEntity.builder().build()).getId();

        orderProductsRepository.save(OrderProductsEntity.builder()
                .orderId(orderId)
                .productId(productEntity1.getId())
                .productAmount(1)
                .build());

        orderProductsRepository.save(OrderProductsEntity.builder()
                .orderId(orderId)
                .productId(productEntity2.getId())
                .productAmount(2)
                .build());

        mockMvc.perform(delete("/orders/" + orderId))
                .andExpect(status().isNoContent());

        assertFalse(orderRepository.findById(orderId).isPresent());
        assertTrue(orderProductsRepository.findAllByOrderId(orderId).isEmpty());
    }
}
