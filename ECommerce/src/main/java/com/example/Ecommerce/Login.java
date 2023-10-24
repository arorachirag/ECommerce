package com.example.Ecommerce;

import java.sql.ResultSet;

public class Login {

    public Customer customerLogin(String email,String password){
        String query = "select * from customer where email = '"+email+"' and password = '"+password+"'";
        DbConnection connection = new DbConnection();
        try{
            ResultSet rs = connection.getQueryTable(query);
            if(rs.next()){
                return new Customer(rs.getInt("id"),
                        rs.getString("name"),rs.getString("email"),
                        rs.getString("password"),rs.getString("mobile")
                        );
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        //System.out.println(login.customerLogin("angad@gmail.com","angad01"));
        Customer customer = login.customerLogin("angad@gmail.com","angad01");
        System.out.println("Welcome " + customer.getName());
    }
}
