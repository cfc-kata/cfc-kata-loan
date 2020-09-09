package com.cfckata.order.dao;

import com.cfckata.order.domain.OrderItem;
import lombok.Data;

import java.math.BigDecimal;

public class OrderItemDO {
    private Long id;
    private String orderId;
    private String prodId;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal subTotal = BigDecimal.ZERO;

    public OrderItemDO() {
    }

    public OrderItemDO(String orderId, OrderItem item) {
        id = item.getId();
        this.orderId = orderId;
        this.prodId = item.getProduct().getId();
        this.amount = item.getAmount();
        this.subTotal = item.getSubTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
