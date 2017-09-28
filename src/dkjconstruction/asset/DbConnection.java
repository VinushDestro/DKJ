/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.asset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author VINUSH
 */
public class DbConnection {
    static final String url = "jdbc:mysql://192.168.8.101:3306/dkj";
    static final String username = "user";
    static final String password = "";
    private static Connection con = null;

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void openConnection() throws ClassNotFoundException, SQLException {

        System.out.println("Connecting database...");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } 
        catch (SQLException e) {
            System.out.println("Error "+ e);
        }
    }
    
    public static Connection getConnection() {
       return con;
    }

    public static void closeConnection() throws SQLException { 
        con.close();
    }
    
    public static void  main (String args[] )throws SQLException, ClassNotFoundException {
        openConnection();
    }
    
}
