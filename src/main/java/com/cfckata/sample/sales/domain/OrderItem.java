package com.cfckata.sample.sales.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem {
    private Long id;
    private String productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal subTotal;

    public OrderItem() {
    }

    public OrderItem(Long id, BigDecimal amount, BigDecimal price, String productId, String productName) {
        if (productId == null) {
            throw new IllegalArgumentException("product is null");
        }

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.subTotal = price.multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
