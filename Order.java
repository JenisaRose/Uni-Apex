package com.cloudkitchen;

// Represents a single customer order
public class Order {
    private static int nextOrderId = 1001;
    private int orderId;
    private String dishName;
    private int quantity;
    private boolean isPrepared;

    public Order(String dishName, int quantity) {
        this.orderId = nextOrderId++;
        this.dishName = dishName;
        this.quantity = quantity;
        this.isPrepared = false;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public String getDishName() { return dishName; }
    public int getQuantity() { return quantity; }
    public boolean isPrepared() { return isPrepared; }
    public void setPrepared(boolean prepared) { isPrepared = prepared; }

    @Override
    public String toString() {
        String status = isPrepared ? "READY" : "PENDING";
        return "Order " + orderId + 
               " | Dish: " + dishName + 
               " | Qty: " + quantity + 
               " | Status: " + status;
    }
}