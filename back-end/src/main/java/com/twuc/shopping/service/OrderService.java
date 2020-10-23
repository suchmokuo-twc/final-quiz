package com.twuc.shopping.service;

import com.twuc.shopping.dto.OrderDto;
import com.twuc.shopping.entity.OrderEntity;
import com.twuc.shopping.entity.OrderProductsEntity;
import com.twuc.shopping.repository.OrderProductsRepository;
import com.twuc.shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderProductsRepository orderProductsRepository) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
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

//    public List<OrderResponseDto> getOrders() {
//        return orderRepository.findAll()
//                .stream()
//                .map(OrderResponseDto::from)
//                .collect(Collectors.toList());
//    }
//
//    public void deleteOrder(String orderId) {
//        orderRepository.deleteById(orderId);
//    }
}
