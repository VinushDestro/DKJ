/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.rawmaterial;

import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author User
 */
public class RawMaterial extends Application {
    private static Stage stage;
    private static AnchorPane Pane;

  
     @Override
    public void start(Stage stage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Rawmaterial.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
   


 // add rawmaterial  
    public static int addrawmaterial(String type,int quantity,Double price,String measurement,int supplier) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into rawmaterial (type,quantity,price,measurement,supplier) values (?,?,?,?,?)");
        
        stmt.setString(1,type);
        stmt.setInt(2,quantity);
        stmt.setDouble(3,price);
        stmt.setInt(5,supplier);
        stmt.setString(4,measurement);
        
        
        int result = stmt.executeUpdate();
      //  DbConnection.closeConnection();

        return result;
    }
    


 //update rawmaterial
    public static int updaterawmaterial(String type,int  quantity) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update rawmaterial set quantity=quantity+? where type=?");
       
        stmt.setInt(1,quantity);
      
        stmt.setString(2,type);
        
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
      //  DbConnection.closeConnection();
        return result;
    }
    
   



//delete rawmaterial
    public static int deleterawmaterial(String type) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from rawmaterial where type=?");
        stmt.setString(1,type);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
      //  DbConnection.closeConnection();
        return result;
    }
  
    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * 
     * 
     * 
     */
    
    
    
    public static int deleterawmaterial(String type, int  quantity) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection = new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        
      //   int s=Integer.parseInt(quantity);
        PreparedStatement stmt = con.prepareStatement("update rawmaterial set quantity=quantity-?   where type=?");
        stmt.setInt(1, quantity);
        stmt.setString(2, type);
      

        try {
            result = stmt.executeUpdate();
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n" + e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
    
    
    
    
    
    //update rawmaterial
    public static int deductmaterial(String type,int  quantity) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update rawmaterial set quantity= quantity-?   where type=?");
       
        stmt.setInt(1,quantity);
      
        stmt.setString(2,type);
        
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
      //  DbConnection.closeConnection();
        return result;
    }
    
   
    
    
    
    
    
    
    public static ObservableList<RawMaterialDetail> getRawmaterial() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<RawMaterialDetail>  rawmaterial= FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from rawmaterial");

        while(rs.next()) {

            String type=rs.getString("type");
            double price =rs.getDouble("price");
            int quantity =rs.getInt("quantity");
            String measurement=rs.getString("measurement");
            int supplierId=rs.getInt("supplier");
          
            rawmaterial.add(new RawMaterialDetail(type,quantity,price,measurement,supplierId));
// creating objects and load it in
        }
      //  DbConnection.closeConnection();
        return rawmaterial;
    }
    
    
    
    
      
      
//
// public static int assignrawmaterial(String id,String type) throws SQLException, ClassNotFoundException{
//        DbConnection DbConnection= new DbConnection();
//        DbConnection.openConnection();
//        Connection con = DbConnection.getConnection();
//        PreparedStatement stmt = con.prepareStatement("insert into rawmaterial (type,quantity,price,supplier) values (?,?,?,?)");
//        
//        stmt.setString(1,type);
//        stmt.setInt(2,quantity);
//        stmt.setDouble(3,price);
//        stmt.setString(4,supplier);
//        
//        
//        int result = stmt.executeUpdate();
//        DbConnection.closeConnection();
//
//        return result;
//    }
//    
       public static void showTenderView() throws IOException {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(RawMaterial.class.getResource("TenderMaterialDetails.fxml"));
          AnchorPane tenderDetail = loader.load();
          
          Stage tenderStage = new Stage();
          tenderStage.setTitle("Tender Detail");
          tenderStage.initModality(Modality.WINDOW_MODAL);
          
          Scene scene = new Scene(tenderDetail);
          tenderStage.setScene(scene);
          tenderStage.showAndWait();
       }

       
       
       
       
       
        public static boolean doCheckAvailble(String materialType ) throws ClassNotFoundException, SQLException {
//        int result = -1;
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection = new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        boolean bool = false;

        try {

            PreparedStatement stmt = con.prepareStatement("Select assignCount  from materialtender where materialType =?");
            stmt.setString(1, materialType );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getInt("assignCount")>0) {

                    bool = true;
                }

            }

        } catch (Exception e) {
            System.err.println("error " + e);

        }
        DbConnection.closeConnection();
        return bool;

    }

       
       
       
       
       
    
}
    
    
    
    
    
    
