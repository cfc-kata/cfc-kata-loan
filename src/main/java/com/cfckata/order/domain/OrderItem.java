package com.cfckata.order.domain;

import com.cfckata.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem {
    private Long id;
    private Product product;
    private BigDecimal amount;
    private BigDecimal subTotal;

    public OrderItem() {
    }

    public OrderItem(Long id, Product product, BigDecimal amount) {
        if (product == null) {
            throw new IllegalArgumentException("product is null");
        }

        this.id = id;
        this.product = product;
        this.amount = amount;
        this.subTotal = product.getPrice().multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
