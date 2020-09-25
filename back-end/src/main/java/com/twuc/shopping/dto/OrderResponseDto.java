package com.twuc.shopping.dto;

import com.twuc.shopping.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto extends BaseDto {

    Integer id;

    Integer amount;

    ProductDto product;

    public static OrderResponseDto from(OrderEntity orderEntity) {
        return OrderResponseDto.builder()
                .id(orderEntity.getId())
                .amount(orderEntity.getAmount())
                .product(ProductDto.from(orderEntity.getProduct()))
                .build();
    }
}
