package com.example.womenshop.util;

import com.example.womenshop.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DBManager {

    StoreFinance storeFinance=new StoreFinance();

    public StoreFinance getStoreFinance() {
        return storeFinance;
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

    // close connection
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myStmt != null) myStmt.close();
            if (myRs != null) myRs.close();
            if (myConn != null) myConn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






}
