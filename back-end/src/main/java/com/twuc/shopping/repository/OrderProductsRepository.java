package com.twuc.shopping.repository;

import com.twuc.shopping.entity.OrderProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends CrudRepository<OrderProductsEntity, Integer> {

    List<OrderProductsEntity> findAll();

    List<OrderProductsEntity> findAllByOrderId(String orderId);
}
