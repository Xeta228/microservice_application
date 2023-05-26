package com.baron.orderservice.service;

import com.baron.orderservice.dto.InventoryResponse;
import com.baron.orderservice.dto.OrderLineItemDto;
import com.baron.orderservice.dto.OrderRequest;
import com.baron.orderservice.entity.Order;
import com.baron.orderservice.entity.OrderLineItem;
import com.baron.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtos().stream().map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItemList);
        List<String> skuCodes = order.getOrderLineItemList().stream().map(OrderLineItem::getSkuCode).toList();
        InventoryResponse[] inventoryResponseArray = webClient.get().uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve()
                .bodyToMono(InventoryResponse[].class).block();

        boolean result = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

        if(result) orderRepository.save(order);
        else throw new IllegalArgumentException("product is not in stock, please try again later");

    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItem.getPrice());
        orderLineItem.setQuantity(orderLineItem.getQuantity());
        orderLineItem.setSkuCode(orderLineItem.getSkuCode());
         return orderLineItem;
    }
}
