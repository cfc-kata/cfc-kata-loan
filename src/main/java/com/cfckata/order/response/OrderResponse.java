package com.cfckata.order.response;

import com.cfckata.order.domain.Order;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private String id;
    private String createTime;
    private String customerId;
    private List<OrderItemResponse> items;
    private int status;
    private String totalPrice;
    private String totalPayment;

    public OrderResponse() {
    }

    public OrderResponse(Order order) {
        id = order.getId();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        createTime = df.format(order.getCreateTime());
        customerId = order.getCustomerId();
        items = order.getItems().stream().map(i -> new OrderItemResponse(i)).collect(Collectors.toList());
        this.status = order.getStatus().getValue();
        this.totalPrice = NumberFormat.getCurrencyInstance().format(order.getTotalPrice());
        this.totalPayment = NumberFormat.getCurrencyInstance().format(order.getTotalPayment());
    }

    public String getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public int getStatus() {
        return status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }
}
