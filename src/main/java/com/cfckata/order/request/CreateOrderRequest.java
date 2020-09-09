package com.cfckata.order.request;

import java.util.Date;
import java.util.List;

public class CreateOrderRequest {
    private Date createTime;
    private String customerId;
    private List<OrderItemRequest> items;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Date createTime, String customerId, List<OrderItemRequest> items) {
        this.createTime = createTime;
        this.customerId = customerId;
        this.items = items;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
