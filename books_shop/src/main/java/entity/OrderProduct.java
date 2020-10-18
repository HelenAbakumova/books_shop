package com.epam.preprod.store.entity;

import java.math.BigDecimal;

public class OrderProduct {
    private final int product;
    private final BigDecimal price;
    private final int count;

    public OrderProduct(int product, BigDecimal price, int count) {
        this.product = product;
        this.price = price;
        this.count = count;
    }

    public int getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
