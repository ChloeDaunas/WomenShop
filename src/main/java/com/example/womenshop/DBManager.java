package com.example.womenshop;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DBManager {

    StoreFinance storeFinance=new StoreFinance();

    public StoreFinance getStoreFinance() {
        return storeFinance;
    }

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
            Connection connection = getConnection(
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
    public void addProduct(Product p) {
        try (Connection conn = connector()) {
            //check if product exist already
            String checkSql ="";
            if (p instanceof Clothes) {
                checkSql ="SELECT COUNT(*) FROM clothes WHERE name = ?";
            }
            else if (p instanceof Shoes) {
                checkSql ="SELECT COUNT(*) FROM shoes WHERE name = ?";
            }
            else{
                checkSql ="SELECT COUNT(*) FROM accessories WHERE name = ?";
            }
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, p.getName());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product already existing");
                alert.setHeaderText(null);
                alert.setContentText("This product already exists in our database.");
                alert.showAndWait();
            }
            else{
                String sql ="";
                if (p instanceof Clothes) {
                    sql = "INSERT INTO clothes (name, purchase_price, sell_price, size) VALUES (?, ?, ?, ?)";

                }
                if (p instanceof Shoes) {
                    sql = "INSERT INTO clothes (name, purchase_price, sell_price, shoe_size) VALUES (?, ?, ?, ?)";

                }
                if (p instanceof Accessories) {
                    sql = "INSERT INTO shoes (name, purchase_price, sell_price) VALUES (?, ?, ?)";

                }

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, p.getName());

                ps.setDouble(2, p.getPurchasePrice());
                ps.setDouble(3, p.getSellPrice());

                if (p instanceof Clothes) {
                    ps.setInt(4, ((Clothes) p).getSize());
                } else {
                    ps.setInt(4, ((Shoes) p).getShoeSize());
                }
                ps.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierProduct(int id, String newName, double newPurchasePrice, double newSellPrice, Integer size, String type) {
        String sql = "";

        try (Connection conn = connector()) {

            if ("Clothes".equals(type)) {
                sql = "UPDATE clothes SET name = ?, purchase_price = ?, sell_price = ?, size = ? WHERE id = ?";
            } else if ("Shoes".equals(type)) {
                sql = "UPDATE shoes SET name = ?, purchase_price = ?, sell_price = ?, shoe_size = ? WHERE id = ?";
            } else if ("Accessories".equals(type)) {
                sql = "UPDATE accessories SET name = ?, purchase_price = ?, sell_price = ? WHERE id = ?";
            } else {
                System.out.println("Unknown type: " + type);
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setDouble(2, newPurchasePrice);
            stmt.setDouble(3, newSellPrice);

            if (!"Accessories".equals(type)) {
                stmt.setInt(4, size);
                stmt.setInt(5, id);
            } else {
                stmt.setInt(4, id);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
