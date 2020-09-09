package com.cfckata.sample.sales.dao;

import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.domain.OrderStatus;

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

    public SalesOrder toOrder() {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(getId());
        salesOrder.setCustomerId(customerId);
        salesOrder.setCreateTime(getCreateTime());
        salesOrder.setVersion(getVersion());
        salesOrder.setTotalPayment(getTotalPayment());
        salesOrder.setStatus(OrderStatus.from(getStatus()));

        return salesOrder;
    }

    public OrderDO(SalesOrder salesOrder) {
        setId(salesOrder.getId());
        setCreateTime(salesOrder.getCreateTime());
        setCustomerId(salesOrder.getCustomerId());
        setStatus(salesOrder.getStatus().getValue());
        setTotalPayment(salesOrder.getTotalPayment());
        setTotalPrice(salesOrder.getTotalPrice());
        setVersion(salesOrder.getVersion());
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
