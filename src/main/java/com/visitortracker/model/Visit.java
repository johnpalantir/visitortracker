package com.visitortracker.model;

import java.util.Date;

public class Visit {

    private String productId;
    private String userId;
    private Date date;

    public Visit() {
    }

    public Visit(String productId, String userId, Date date) {
        this.productId = productId;
        this.userId = userId;
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", date=" + date +
                '}';
    }
}
