package com.cfckata.sample.product;

import java.math.BigDecimal;

public class ProductDO {
    private String id;
    private String name;
    private BigDecimal price =BigDecimal.ZERO;

    public Product toProduct() {
        return new Product(getId(), getName(), getPrice());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
