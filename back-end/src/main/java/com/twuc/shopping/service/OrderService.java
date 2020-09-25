package com.twuc.shopping.service;

import com.twuc.shopping.dto.OrderRequestDto;
import com.twuc.shopping.dto.OrderResponseDto;
import com.twuc.shopping.entity.OrderEntity;
import com.twuc.shopping.entity.ProductEntity;
import com.twuc.shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(OrderRequestDto orderDto) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findByProductId(orderDto.getProductId());

        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            orderEntity.setAmount(orderEntity.getAmount() + 1);
            orderRepository.save(orderEntity);
        } else {
            orderRepository.save(OrderEntity.builder()
                    .amount(1)
                    .product(ProductEntity.builder()
                            .id(orderDto.getProductId())
                            .build())
                    .build());
        }
    }

    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
