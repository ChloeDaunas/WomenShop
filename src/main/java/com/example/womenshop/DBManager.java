package com.example.womenshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    StoreFinance storeFinance=new StoreFinance();

    // Loading the products

    //Loading clothes
    public List<Clothes> loadClothes() {
        List<Clothes> AllClothes = new ArrayList<>();
        Connection myConn = this.connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM clothes";
            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                Clothes c = new Clothes(
                        myRs.getString("name"),
                        myRs.getDouble("purchase_price"),
                        myRs.getDouble("sell_price"),
                        myRs.getInt("size"),
                        storeFinance
                );
                AllClothes.add(c);
            }

            this.close(myConn, myStmt, myRs);
            return AllClothes;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Loading shoes
    public List<Shoes> loadShoes() {
        List<Shoes> AllShoes = new ArrayList<>();
        Connection myConn = this.connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM shoes";
            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                Shoes s = new Shoes(
                        myRs.getString("name"),
                        myRs.getDouble("purchase_price"),
                        myRs.getDouble("sell_price"),
                        myRs.getInt("shoe_size"),
                        storeFinance
                );
                AllShoes.add(s);
            }

            this.close(myConn, myStmt, myRs);
            return AllShoes;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Loading accessories
    public List<Accessories> loadAccessories() {
        List<Accessories> AllAccessories = new ArrayList<>();
        Connection myConn = this.connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM accessories";
            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                Accessories a = new Accessories(
                        myRs.getString("name"),
                        myRs.getDouble("purchase_price"),
                        myRs.getDouble("sell_price"),
                        storeFinance
                );
                AllAccessories.add(a);
            }

            this.close(myConn, myStmt, myRs);
            return AllAccessories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //open the connection
    public Connection connector() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/womenshop",
                    "root",          // ton utilisateur MySQL
                    "Ar0bazze$" // ton mot de passe
            );
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // clothe connection
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myStmt != null) myStmt.close();
            if (myRs != null) myRs.close();
            if (myConn != null) myConn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //methode to add a product
}
