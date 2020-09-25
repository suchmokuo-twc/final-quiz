package com.twuc.shopping.controller;

import com.twuc.shopping.dto.OrderRequestDto;
import com.twuc.shopping.dto.OrderResponseDto;
import com.twuc.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/orders", produces = "application/json; charset=utf-8")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createOrder(@Valid @RequestBody OrderRequestDto orderDto) {
        orderService.createOrder(orderDto);
    }

    @GetMapping
    @ResponseBody
    List<OrderResponseDto> getOrders() {
        return orderService.getOrders();
    }
}
