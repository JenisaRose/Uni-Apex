package com.cloudkitchen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CloudKitchenManager {

    // Simulates the list of current orders
    private List<Order> pendingOrders = new ArrayList<>();
    
    // Simulates the inventory: Ingredient Name -> Quantity in Stock
    private Map<String, Integer> inventory = new HashMap<>();

    public CloudKitchenManager() {
        // Initialize basic inventory stock
        inventory.put("Burger Patties", 50);
        inventory.put("Buns", 100);
        inventory.put("Salad Mix", 75);
        inventory.put("Pizza Dough", 30);
    }

    // --- Order Placement ---
    public void placeNewOrder(String dish, int qty) {
        if (checkInventory(dish, qty)) {
            Order newOrder = new Order(dish, qty);
            pendingOrders.add(newOrder);
            System.out.println("\n✅ Order " + newOrder.getOrderId() + " for " + dish + " placed successfully!");
            // Simulate deducting stock upon successful order placement
            deductStock(dish, qty);
        } else {
            System.out.println("\n❌ FAILED to place order for " + dish + ". Insufficient stock.");
        }
    }

    // --- Inventory Check Logic ---
    private boolean checkInventory(String dish, int qty) {
        int requiredStock = 0;
        String requiredIngredient = "";

        // Simplified logic: Map dish to a single primary ingredient
        if (dish.equalsIgnoreCase("Burger")) {
            requiredIngredient = "Burger Patties";
            requiredStock = qty;
        } else if (dish.equalsIgnoreCase("Salad")) {
            requiredIngredient = "Salad Mix";
            requiredStock = qty / 2; // Assume 2 salads per unit of mix
        } else if (dish.equalsIgnoreCase("Pizza")) {
            requiredIngredient = "Pizza Dough";
            requiredStock = qty;
        } else {
            System.out.println("Dish '" + dish + "' is unknown.");
            return false;
        }

        int currentStock = inventory.getOrDefault(requiredIngredient, 0);
        return currentStock >= requiredStock;
    }
    
    // --- Stock Deduction ---
    private void deductStock(String dish, int qty) {
        // Re-use logic from checkInventory to find ingredient/required amount
        // (Simplified to avoid repetition, real code would refactor this)
        String requiredIngredient = "";
        int requiredStock = 0;
        
        if (dish.equalsIgnoreCase("Burger")) {
            requiredIngredient = "Burger Patties";
            requiredStock = qty;
        } else if (dish.equalsIgnoreCase("Salad")) {
            requiredIngredient = "Salad Mix";
            requiredStock = qty / 2;
        } else if (dish.equalsIgnoreCase("Pizza")) {
            requiredIngredient = "Pizza Dough";
            requiredStock = qty;
        } else {
            return;
        }

        inventory.put(requiredIngredient, inventory.get(requiredIngredient) - requiredStock);
    }

    // --- Kitchen Status Update ---
    public void updateOrderStatus(int id) {
        for (Order order : pendingOrders) {
            if (order.getOrderId() == id && !order.isPrepared()) {
                order.setPrepared(true);
                System.out.println("\n✅ Order " + id + " status updated to READY for pickup.");
                return;
            }
        }
        System.out.println("\n⚠️ Order " + id + " not found or already prepared.");
    }
    
    // --- Display Methods ---
    public void displayAllOrders() {
        System.out.println("\n--- Current Order Queue (" + pendingOrders.size() + " Total) ---");
        if (pendingOrders.isEmpty()) {
             System.out.println("The kitchen queue is empty.");
             return;
        }
        for (Order order : pendingOrders) {
            System.out.println(order);
        }
        System.out.println("----------------------------------------");
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory Stock ---");
        inventory.forEach((item, stock) -> 
            System.out.println(item + ": " + stock + " units")
        );
        System.out.println("-----------------------------");
    }

    // --- Main Runner ---
    public static void main(String[] args) {
        CloudKitchenManager manager = new CloudKitchenManager();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("--- CLOUD KITCHEN MANAGEMENT SYSTEM (Simple Java) ---");
        
        // Initial setup and demonstration
        manager.placeNewOrder("Burger", 10);
        manager.placeNewOrder("Salad", 4); 
        manager.placeNewOrder("Pizza", 30); // Should fail due to low stock

        do {
            System.out.println("\n\n-- MENU --");
            System.out.println("1. Place New Order (Simulated)");
            System.out.println("2. Update Order Status to READY");
            System.out.println("3. View All Orders");
            System.out.println("4. View Inventory");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Dish (Burger/Salad/Pizza): ");
                        String dish = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        if (scanner.hasNextInt()) {
                             int qty = scanner.nextInt();
                             manager.placeNewOrder(dish, qty);
                        } else {
                            System.out.println("Invalid quantity.");
                            scanner.nextLine();
                        }
                        break;
                    case 2:
                        System.out.print("Enter Order ID to mark as READY (e.g., 1001): ");
                        if (scanner.hasNextInt()) {
                             int id = scanner.nextInt();
                             manager.updateOrderStatus(id);
                        } else {
                            System.out.println("Invalid ID.");
                            scanner.nextLine();
                        }
                        break;
                    case 3:
                        manager.displayAllOrders();
                        break;
                    case 4:
                        manager.displayInventory();
                        break;
                    case 0:
                        System.out.println("Exiting system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear buffer
            }

        } while (choice != 0);
        
        scanner.close();
    }
}