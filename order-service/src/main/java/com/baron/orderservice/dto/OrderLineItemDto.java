package com.baron.orderservice.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderLineItemDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
