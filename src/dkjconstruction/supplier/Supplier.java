/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.supplier;

import dkjconstruction.DbConnection;
import dkjconstruction.rawmaterial.RawMaterialDetail;
import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
/**
 *
 * @author User
 */
public class Supplier extends Application {
 
     @Override
    public void start(Stage stage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Supplier.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
   


 // add supplier  
    public static int addsupplier(String supplierid,String name,String nic,String contact) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into supplier (supplierid,name,nic,contact) values (?,?,?,?)");
        
        stmt.setString(1,supplierid);
        stmt.setString(2,name);
        stmt.setString(3,nic);
        stmt.setString(4,contact);
        
        
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();

        return result;
    }
    


 //update supplier
    public static int updatesupplier(String supplierid,String name,String nic,String contact) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update supplier set name=?,nic=?,contact=? where supplierId=?");
        stmt.setString(4,supplierid);
        stmt.setString(1,name);
        stmt.setString(2,nic);
        stmt.setString(3,contact);
        
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
    
   



//delete supplier
    public static int deleteSupplier(String supplierid) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from supplier where supplierid=?");
        stmt.setString(1,supplierid);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
  
        public static ObservableList<SupplierDetail> getSupplier() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<SupplierDetail>  supplier= FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from supplier");

        while(rs.next()) {

            String supplierid=rs.getString("supplierid");
            String name =rs.getString("name");
            String  nic =rs.getString("nic");
            String contact=rs.getString("contact");
          
            supplier.add(new SupplierDetail(supplierid,name,nic,contact));
// creating objects and load it in
        }
        DbConnection.closeConnection();
        return supplier;
    }
      
    
    
}