package com.cfckata.order.request;

import java.math.BigDecimal;

public class OrderItemRequest {
    private String productId;
    private BigDecimal amount;

    public OrderItemRequest() {
    }

    public OrderItemRequest(String productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
