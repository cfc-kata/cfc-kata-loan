package com.cfckata.order.dao;

import com.cfckata.order.domain.Order;
import com.cfckata.order.domain.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDO {
    private String id;
    private Date createTime;
    private String customerId;
    private int status;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalPayment = BigDecimal.ZERO;
    private int version;

    public OrderDO() {
    }

    public Order toOrder() {
        Order order = new Order();
        order.setId(getId());
        order.setCreateTime(getCreateTime());
        order.setVersion(getVersion());
        order.setTotalPayment(getTotalPayment());
        order.setTotalPrice(getTotalPrice());
        order.setStatus(OrderStatus.from(getStatus()));

        return order;
    }

    public OrderDO(Order order) {
        setId(order.getId());
        setCreateTime(order.getCreateTime());
        setCustomerId(order.getCustomer().getId());
        setStatus(order.getStatus().getValue());
        setTotalPayment(order.getTotalPayment());
        setTotalPrice(order.getTotalPrice());
        setVersion(order.getVersion());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
