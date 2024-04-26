package com.example.Bank.Entity;

public class OrderReport {
    private String orderNumber;
    private String phoneNumber;

    public OrderReport(String orderNumber, String phoneNumber) {
        this.orderNumber = orderNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
