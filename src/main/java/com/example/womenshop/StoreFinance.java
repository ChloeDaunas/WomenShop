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

    public double getCapital() { return capital; }

    public double getCost() {
        return cost;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }
}
