package com.twuc.shopping.dto;

import com.twuc.shopping.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseDto {

    Integer id;

    @NotNull
    String name;

    @NotNull
    Integer price;

    @NotNull
    String unit;

    @NotNull
    String image;

    public static ProductDto from(ProductEntity productEntity) {
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .unit(productEntity.getUnit())
                .image(productEntity.getImage())
                .build();
    }
}
