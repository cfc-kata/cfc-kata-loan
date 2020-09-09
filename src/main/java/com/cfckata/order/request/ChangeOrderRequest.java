package com.cfckata.order.request;

import java.util.List;

public class ChangeOrderRequest {
    private String orderId;
    private String customerId;
    private List<OrderItemRequest> items;

    public ChangeOrderRequest(String orderId, String customerId, List<OrderItemRequest> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
    }

    public ChangeOrderRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }
}
