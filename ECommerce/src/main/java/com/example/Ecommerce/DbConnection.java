package com.example.Ecommerce;
import java.sql.*;

public class DbConnection {
    private final String dbURL = "jdbc:mysql://localhost:3306/ecommerce";

    private  final String userName = "root";

    private final String password = "root";


    private Statement getStatement(){
        try{
            Connection conn = DriverManager.getConnection(dbURL,userName,password);
            return conn.createStatement();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getQueryTable(String query){
        try{
            Statement s = getStatement();
            return s.executeQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public int updateDatabase(String query){
        try{
            Statement s = getStatement();
            return s.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }



    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable("select * from customer;");

        if(rs!=null){
            System.out.println("Connection Successful");
        } else {
            System.out.println("Connection failed");
        }
    }
}
