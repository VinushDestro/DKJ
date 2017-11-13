/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.transport;

import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class Transport {
    public static ObservableList<TransportDetail> getTransport() throws IOException, ClassNotFoundException, SQLException {
            ObservableList<TransportDetail>  transport= FXCollections.observableArrayList();

            DbConnection.openConnection();
            Connection con=DbConnection.getConnection();
            Statement stmt =con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from transport");

            while(rs.next()) {
                String tripId=rs.getString(1);
                String RegNo=rs.getString(2);
                String tenderId=rs.getString(3);
                String destination=rs.getString(4);
                Date date=rs.getDate(5);
                Double cost=rs.getDouble(6);
                
                transport.add(new TransportDetail(tripId,RegNo,tenderId,destination,date,cost));
            }
                return transport;
        }
    
    public static int addTransport(String regNo,String tenderId,String destination,Date date,double cost) throws SQLException, ClassNotFoundException{
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into transport (regno,tenderid,destination,date,cost) values (?,?,?,?,?)");
        
        stmt.setString(1,regNo);
        stmt.setString(2,tenderId);
        stmt.setString(3,destination);
        stmt.setDate(4,date);
        stmt.setDouble(5,cost);
        
        int result = stmt.executeUpdate();

        return result;
    }
    
//    public static int updateTransport(String regNo,Date date,double cost,String tripId) throws SQLException, ClassNotFoundException {
//        int result = -1;
//        Alert alert= new Alert(Alert.AlertType.INFORMATION);
//        DbConnection DbConnection= new DbConnection();
//
//        DbConnection.openConnection();
//        Connection con = DbConnection.getConnection();
//
//        PreparedStatement stmt = con.prepareStatement("update transport set date=?,cost=?,regno=? where tripid=?");
//        stmt.setDate(1,date);
//        stmt.setDouble(2,cost);
//        stmt.setString(3,regNo);
//        stmt.setString(4,tripId);
//        
//        try{
//            result = stmt.executeUpdate();
//        }
//        catch (SQLException e){
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Invalid Entry\n"+e.getMessage());
//            alert.show();
//
//        }
//        return result;
//    }
    
    public static int updateTransport(Date date,String tripId) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update transport set date=? where tripid=?");
        stmt.setDate(1,date);
        stmt.setString(2,tripId);
        
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
    
    public static int updateTransport(double cost,String tripId) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update transport set cost=? where tripid=?");
      
        stmt.setDouble(1,cost);
        stmt.setString(2,tripId);
        
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
    
    public static int updateTransport(String regNo,String tripId) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update transport set regno=? where tripid=?");
      
        stmt.setString(1,regNo);
        stmt.setString(2,tripId);
        
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
    
    public static int deleteTransport(String tripId) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from transport where tripid=?");
        stmt.setString(1,tripId);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
        return result;
    }
}
