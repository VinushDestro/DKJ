/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.settings;

import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class Admin {
    
        public static ObservableList<AdminDetail> getAdmin() throws IOException, ClassNotFoundException, SQLException {
            ObservableList<AdminDetail>  admin= FXCollections.observableArrayList();

            DbConnection.openConnection();
            Connection con=DbConnection.getConnection();
            Statement stmt =con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from user");

            while(rs.next()) {
                String userId=rs.getString(1);
                String userType=rs.getString(2);
                String date=rs.getString(3);
                String username=rs.getString(4);
                String password=rs.getString(5);
                
                admin.add(new AdminDetail(userId,userType,date,username,password));
            }
                return admin;
        }
    public static int addAdmin(String userId,String userType,Date assignDate,String username,String password) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into user values (?,?,?,?,?)");
        
        stmt.setString(1,userId);
        stmt.setString(2,userType);
        stmt.setDate(3,assignDate);
        stmt.setString(4,username);
        stmt.setString(5,password);
        
        int result = stmt.executeUpdate();

        return result;
    }
    
    public static int updateAdmin(String userId,String userType) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update user set userType=? where userId=?");
        stmt.setString(1,userType);
        stmt.setString(2,userId);
        
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
        return result;
    }
    
    public static int deleteAdmin(String userId) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from user where userId=?");
        stmt.setString(1,userId);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
        return result;
    }
    
    
}
