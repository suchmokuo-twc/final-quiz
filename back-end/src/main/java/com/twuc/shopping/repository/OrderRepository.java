package com.twuc.shopping.repository;

import com.twuc.shopping.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    List<OrderEntity> findAll();
}
