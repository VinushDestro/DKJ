/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author KishBelic
 */
public class TenderManagement extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("TenderHome.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    //alertbox Information
    public static void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox warning
    public static void alertboxWarn(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox Confirmation
    public static boolean alertboxConfirm(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirmation");
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    public static void insertEsti_Cost(String tid, double cost) {
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE tender set estimatedCost = '" + cost + "' where tenderId = '" + tid + "'");
            pst.execute();

        } catch (Exception e) {
            System.out.println("Update  error");

        }

    }

//    public static void notification() {
//        Notifications noti = Notifications.create()
//                .graphic(null)
//                .position(Pos.TOP_RIGHT)
//                .text("Added Successfully")
//                .title("chumma")
//                .hideAfter(Duration.seconds(3));
//
//        noti.showConfirm();
//    }

    public static double findCost(String tid, char a, char b) {

        
        double est_material = 0;
        double actual_material = 0;

        Connection con = DbConnection.getConnection();

        

        //material
        try {

            PreparedStatement pst = con.prepareStatement("Select mt.tenderId,mt.materialType,r.price*mt.materialCount as 'rCost',r.price*mt.assignCount as 'assignCost'\n"
                    + "from rawmaterial r\n"
                    + "right join materialtender mt on r.type=mt.materialType where mt.tenderId = '" + tid + "'");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                est_material = est_material + rs.getDouble("rCost");
                actual_material = actual_material + rs.getDouble("assignCost");
            }

        } catch (Exception e) {
            System.err.println("error esti" + e);

        }

        double estimatedCost =  est_material;
        double actualCost =  actual_material;

        
        
         if (a == 'e' && b == 'm') {
            return est_material;
         } else if (a == 'a' && b == 'm') {
            return actual_material;
        } else if (a == 'e' && b == 'f') {
            return estimatedCost;
        } else if (a == 'a' && b == 'f') {
            return actualCost;
        } else {

            return -5;
        }

    }

    public static boolean checkRequired(String tid) {

        boolean bool = false ;
        try {

            Connection con = DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT mt.materialCount FROM materialtender mt where mt.tenderId = '" + tid + "'\n"
                    + "Union\n"
                    + "SELECT je.noOfEmployee FROM jobemployee je where je.tenderId = '" + tid + "'\n"
                    + "Union\n"
                    + "SELECT et.count FROM equiptender et where et.tenderId = '" + tid + "'\n"
                    + "Union\n"
                    + "SELECT ja.assetCount FROM jobasset ja where ja.tenderId = '" + tid + "' ");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                bool = true;
            }

        } catch (Exception e) {
            System.out.println("error " + e);
        }
        return bool;

    }



public static int getEmp(String tid,char c)
    {
    int Eemp = 0;
    int Aemp = 0;
        try {
            
                Connection con = DbConnection.getConnection();
                PreparedStatement pst = con.prepareStatement("select * from jobemployee where tenderId = '" + tid + "'");
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    Eemp = Eemp + rs.getInt(2);
                    Aemp = Aemp + rs.getInt(3);
                    
                }

            } catch (Exception e) {
            }
        
       if(c=='e')
       {
       return Eemp;
       }
       else if(c=='a')
       {
       return Aemp;
        }
       else
       {
           return -1;
       }
       
    }

public static boolean checkClosedCancelOngoing(String tid,String status)
    {
    
        Connection con = DbConnection.getConnection();
     
     boolean bool =false;
     
     //check closed or cancelled
        try {

            PreparedStatement pst = con.prepareStatement("Select status from tender where tenderId = '"+ tid + "' and status='"+status+"' ");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                
                bool = true;
                
            }

        } catch (Exception e) {
            System.err.println("error " + e);

        }
        return bool;
        
    }
    

    
    public static boolean checkOngoing(String tid)
    {
    
        Connection con = DbConnection.getConnection();
     
     boolean bool =false;
     
     //material
        try {

            PreparedStatement pst = con.prepareStatement("Select tenderId,materialType,assignCount from materialtender where tenderId = '" + tid + "'");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                
                if (rs.getInt("assignCount") > 0) {
                     
                   
                   
                    bool = true;     
                }
                
            }

        } catch (Exception e) {
            System.err.println("error " + e);

        }
     
     //equip
        try {

            PreparedStatement pst = con.prepareStatement("Select tenderId,equipName,assignCount from equiptender where tenderId = '" + tid + "'");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                
                if (rs.getInt("assignCount") > 0) {
                    bool = true;     
                }
                
            }

        } catch (Exception e) {
            System.err.println("error " + e);

        }
     
    //asset
        try {

            PreparedStatement pst = con.prepareStatement("Select tenderId,assetType,assignCount from jobasset where tenderId =  '" + tid + "'");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                
                if (rs.getInt("assignCount") > 0) {
                    bool = true;     
                }
                
            }

        } catch (Exception e) {
            System.out.println("error " + e);

        }
        
        
     //asset
        try {

            PreparedStatement pst = con.prepareStatement("Select tenderId,assignCount from jobemployee where tenderId =  '" + tid + "'");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                
                if (rs.getInt("assignCount") > 0) {
                    bool = true;     
                }
                
            }

        } catch (Exception e) {
            System.out.println("error " + e);

        }   
      
        
        
        
        
   
    return bool;
    }
    
    

}
