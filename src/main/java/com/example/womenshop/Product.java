package com.example.womenshop;

public abstract class Product implements Discount, Comparable<Product> {
    private static int number=0;
    private String name;
    private double purchase_price;
    private double sell_price;
    private double discount_price;
    private int nbItems;
    private static double capital = 10000.0;
    private static double income=0;
    private static double cost=0;




    public Product(String name, double purchase_price, double sell_price) {
        this.name = name;
        this.discount_price = 0;
        this.nbItems = 0;
        this.number++;

        try{
            if(purchase_price >=0) {
                this.purchase_price = purchase_price;
            }
            else{
                throw new IllegalArgumentException("Negative price!");
            }

            if(sell_price >=0) {
                this.sell_price = sell_price;
            }
            else{
                throw new IllegalArgumentException("Negative price!");
            }
        }
        catch(IllegalArgumentException e){
            System.out.printf(e.getMessage());
        }

    }

    public void sell(int nb){
        try{
            if(this.nbItems < nb ){
                throw new IllegalArgumentException("Product Unavailable");
            }
            else{
                this.nbItems-=nb;
                income+=nb*this.sell_price;

                System.out.println(nb + " item(s) of " + name + " sold successfully.");
            }
        }
        catch(IllegalArgumentException e){
            //on affiche le message
            //l'exeption n'affiche rien elle attrape juste le pb
            System.out.println(e.getMessage());
        }
    }

    public void purchase(int nb){
        try{
            if(capital<nb*purchase_price ){
                throw new IllegalArgumentException("Product Unavailable");
            }
            else{
                nbItems+=nb;
                capital-=nb*purchase_price;
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void unApplyDiscount(){
        discount_price=0;
    }

    @Override
    public int compareTo(Product other){
        return Double.compare(this.sell_price, other.sell_price);
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchase_price;
    }

    public double getSellPrice() {
        return sell_price;
    }

    public double getDiscountPrice() {
        return discount_price;
    }

    public int getNbItems() {
        return nbItems;
    }

    public static double getCapital() {
        return capital;
    }

    public static double getIncome() {
        return income;
    }
    public static double getCost() {
        return cost;
    }

    //Setters
    public void setDiscountPrice(double discountPrice) {
        this.discount_price = discountPrice;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    public static void setCapital(double newCapital) {
        capital = newCapital;
    }

    public static void setIncome(double newIncome) {
        income = newIncome;
    }

    public void setPurchasePrice(double newPurchasePrice) {
        purchase_price = newPurchasePrice;
    }

    public void setSellPrice(double newSellPrice) {
        sell_price = newSellPrice;
    }

    public void setNumber(int newNumber) {
        number = newNumber;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setDiscount(double newDiscount) {
        discount_price = newDiscount;

    }

    public static void setCost(double newCost) {
        cost = newCost;
    }

    public String toString(){return number + " " + name + " purchase price:" + purchase_price + " sell price:" + sell_price + " discount price:" + discount_price + " nb items:" + nbItems;}


}

