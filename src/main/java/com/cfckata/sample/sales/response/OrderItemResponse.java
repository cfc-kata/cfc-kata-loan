package com.cfckata.sample.sales.response;

import com.cfckata.sample.sales.domain.OrderItem;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderItemResponse {
    private Long id;
    private String productId;
    private String productName;
    private String amount;
    private String subTotal;

    public OrderItemResponse() {
    }

    public OrderItemResponse(OrderItem item) {
        this.id = item.getId();
        this.productId = item.getProductId();
        this.productName = item.getProductName();
        this.amount = NumberFormat.getNumberInstance(Locale.CHINA).format(item.getAmount());
        this.subTotal = NumberFormat.getCurrencyInstance(Locale.CHINA).format(item.getSubTotal());
    }

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getAmount() {
        return amount;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
