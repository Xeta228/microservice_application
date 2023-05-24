package com.baron.orderservice.service;

import com.baron.orderservice.dto.OrderLineItemDto;
import com.baron.orderservice.dto.OrderRequest;
import com.baron.orderservice.entity.Order;
import com.baron.orderservice.entity.OrderLineItem;
import com.baron.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtos().stream().map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItemList);
        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItem.getPrice());
        orderLineItem.setQuantity(orderLineItem.getQuantity());
        orderLineItem.setSkuCode(orderLineItem.getSkuCode());
         return orderLineItem;
    }
}
