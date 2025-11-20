package com.example.womenshop;

public class StoreFinance {
    private double capital = 10000;
    private double income = 0;
    private double cost = 0;

    public boolean canBuy(double totalCost) { return totalCost <= capital; }
    public void registerPurchase(double totalCost) {
        capital = capital-totalCost;
        cost = cost + totalCost;
    }
    public void registerSale(double totalCost) {
        capital = capital+totalCost;
        income = income + totalCost;
    }
}
