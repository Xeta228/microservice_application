package com.baron.orderservice.dto;

import com.baron.orderservice.entity.OrderLineItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemDtos;
}
