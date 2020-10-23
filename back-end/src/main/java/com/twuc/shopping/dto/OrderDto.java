package com.twuc.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends BaseDto {

    private String id;

    @Valid
    @NotEmpty
    private List<OrderProductDto> products;

    @EqualsAndHashCode(callSuper = false)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDto extends BaseDto {

        @Valid
        @NotNull
        private ProductDto product;

        @NotNull
        @Min(1)
        private Integer amount;
    }
}
