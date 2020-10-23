package com.twuc.shopping.service;

import com.twuc.shopping.dto.OrderDto;
import com.twuc.shopping.dto.ProductDto;
import com.twuc.shopping.entity.OrderEntity;
import com.twuc.shopping.entity.OrderProductsEntity;
import com.twuc.shopping.entity.ProductEntity;
import com.twuc.shopping.repository.OrderProductsRepository;
import com.twuc.shopping.repository.OrderRepository;
import com.twuc.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderProductsRepository orderProductsRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = OrderEntity.builder().build();
        orderRepository.save(orderEntity);

        orderDto.getProducts().forEach(orderProductDto -> {
            OrderProductsEntity orderProductsEntity = OrderProductsEntity.builder()
                    .orderId(orderEntity.getId())
                    .productAmount(orderProductDto.getAmount())
                    .productId(orderProductDto.getProduct().getId())
                .build();

            orderProductsRepository.save(orderProductsEntity);
        });
    }

    @Transactional
    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderEntity::getId)
                .map(this::getOrderInfoById)
                .collect(Collectors.toList());
    }

    private OrderDto getOrderInfoById(String orderId) {
        List<OrderDto.OrderProductDto> products = orderProductsRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderProductsEntity -> {
                    Integer productId = orderProductsEntity.getProductId();
                    Integer productAmount = orderProductsEntity.getProductAmount();
                    ProductEntity productEntity = productRepository.findById(productId).get();
                    return OrderDto.OrderProductDto.builder()
                            .product(ProductDto.from(productEntity))
                            .amount(productAmount)
                            .build();
                })
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(orderId)
                .products(products)
                .build();
    }

    @Transactional
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
        orderProductsRepository.deleteAllByOrderId(orderId);
    }
}
