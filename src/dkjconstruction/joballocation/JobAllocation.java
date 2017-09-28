/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;
import dkjconstruction.DbConnection;
import java.sql.*;

import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**
 *
 * @author Ranjitha
 */
public class JobAllocation {
    //update rawmaterial setquantity=quantity+value where type = materialtype
    /* 
    public static ObservableList<VINU> getAsset() throws SQLException, ClassNotFoundException {
        ObservableList<VINU> asset = FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select regNo,name,type from asset");

        while (rs.next()) {
            String vehicleNo= rs.getString("regNo");
            String vehicleName = rs.getString("name");
            
            String vehicleType = rs.getString("type");


            asset.add(new VINU(vehicleNo,vehicleName,vehicleType ));

        }
        DbConnection.closeConnection();
        return asset;
        
     }
    //Inserting values into travel table.........------------------------------------
    
   /* public static int assignTravel(String vehicleRegNo,String TenderID, String Destination) throws SQLException, ClassNotFoundException{
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        PreparedStatement stmt = con.prepareStatement("insert into transport values(?,?)");
            stmt.setString(2,vehicleRegNo);
            stmt.setString(3,TenderID);
            stmt.setString(4,Destination);
            
             int result = -1;

            try {
                result = stmt.executeUpdate();
            } catch (Exception e) {
                alert.setTitle("Error in Assigning vehicles");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.show();
            }
            
            DbConnection.closeConnection();
            return result;
    }
    */
    // INSERTING VALUES TO TENDER EMPLOYEE TABLE
    /*
    public static int assignEmployee(String empTenderId) throws SQLException, ClassNotFoundException{
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        PreparedStatement stmt = con.prepareStatement("insert into emptender values(?)");
           // stmt.setString(1,VehicleRegNo);
            stmt.setString(2,empTenderId);
            //stmt.setString(3,Destination);
            
             int result = -1;

            try {
                result = stmt.executeUpdate();
            } catch (Exception e) {
                alert.setTitle("Error in Adding Employees");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.show();
            }
            
            DbConnection.closeConnection();
            return result;
    }
    */
    // entering Values to jobEmployee Table;
    /*
    public static int assignJobEmployee(String jobTenderId, int jobEmpCount ) throws ClassNotFoundException, ClassCastException, SQLException{
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        PreparedStatement stmt = con.prepareStatement("insert into jobemployee values(?,?)");
           // stmt.setString(1,VehicleRegNo);
            stmt.setString(1,jobTenderId);
            stmt.setInt(2, jobEmpCount);
            
             int result = -1;

            try {
                result = stmt.executeUpdate();
            } catch (Exception e) {
                alert.setTitle("Error in Adding Employee Count");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.show();
            }
            
            DbConnection.closeConnection();
            return result;
    }
    
    */
    
// Retriving data from asset table---------------------------------------------------------
    
    /*
    public static ObservableList<VINU> getTravel() throws SQLException {
        ObservableList<VINU> travel = FXCollections.observableArrayList();
        
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("select regNo, name , type  from asset where availability='available' ");
            
            while (rs.next()){
            String regNo = rs.getString("regNo");
            String name = rs.getString("name");
            String type = rs.getString("type");
            
            
            }
            
            DbConnection.closeConnection();
            return travel;
    }
    */
    
    // retrive data from employee table------------------------------
    /*
    public static ObservableList<ASRAJ> getEmployee() throws SQLException, ClassNotFoundException {
        ObservableList<ASRAJ> employee = FXCollections.observableArrayList();
        
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        Statement stmt  = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("select employeeID, Name,Type from employee where Availability= 'Available' ");
        
        while (rs.next()){
        String empid = rs.getString("empid");
        String empName= rs.getString("name");
        String type = rs.getString("empType");
        
            employee.add(new ASRAJ(empid,empName,type));
        }
        DbConnection.closeConnection();
        return employee;
        
}
    */
    
    /*
    // RETRIVING DETAILS FROM TENDER TABLE
    public static ObservableList<KISHANTH> getTender() throws SQLException, ClassNotFoundException {
    ObservableList<KISHANTH> tender = FXCollections.observableArrayList();
    
    DbConnection.openConnection();
    Connection con = DbConnection.getConnection();
    Statement stmt = con.createStatement();
    
    ResultSet rs = stmt.executeQuery("select tenderId, workType, category, workingPlace, cost from tender");
    
    while (rs.next())  {
    String tenderId= rs.getString("tenderId");
    String workType = rs.getString("workType");
    String category = rs.getString("category");
    String workingPlace = rs.getString("workingPlace");
    Double cost = rs.getDouble("estimatedCost");
    
    tender.add(new KISHANTH(tenderId,workType,category,workingPlace,cost));
    }
    DbConnection.closeConnection();
    return tender;
    }
    */
    
    
    
    
}  
    

