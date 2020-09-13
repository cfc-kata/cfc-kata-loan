package com.cfckata.sample.sales.domain;

import com.github.meixuesong.aggregatepersistence.Versionable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SalesOrder implements Versionable, Serializable {
    private String id;
    private Date createTime;
    private String customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalPayment = BigDecimal.ZERO;
    private int version;

    public void checkout(Payment payment) {
        if (status != OrderStatus.NEW) {
            throw new OrderPaymentException("The order status is not for payment.");
        }

        totalPayment = payment.getAmount();
        validatePayments();
        this.status = OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        totalPrice = items.stream().map(OrderItem::getSubTotal).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public void discard() {
        if (status != OrderStatus.NEW) {
            throw new SalesOrderStatusException("Only new order can be discard.");
        }

        this.status = OrderStatus.DISCARD;
    }

    private void validatePayments() {
        if (totalPayment.compareTo(totalPrice) != 0) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            throw new OrderPaymentException(String.format("Payment (%s) is not equals to total price (%s)",
                    decimalFormat.format(totalPayment), decimalFormat.format(totalPrice)));
        }
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
