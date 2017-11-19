/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ranjitha
 */
public class PendingMaterialController implements Initializable {
    
     private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    private ObservableList<matTender> dataMatTendpending;
    private ObservableList<Material> datamatpending;

    @FXML
    private TableView pendingmaterial;
    @FXML
    private TableColumn pendingmattype;
    @FXML
    private TableColumn pendingmatcount;
    @FXML
    private JFXTextField pendingmaterialtender;
    @FXML
    private JFXTextField pendingtendermaterialtype;
    @FXML
    private JFXTextField pendingMatCount;
    @FXML
    private JFXTextField searchfield;
    @FXML
    private TableView pendingtendMatTbl;
    @FXML
    private TableColumn pendingmatTendID;
    @FXML
    private TableColumn pendingmatTendType;
    @FXML
    private TableColumn pendingmatReq;
    @FXML
    private TableColumn pendingmatAssign;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         dataMatTendpending = FXCollections.observableArrayList();
        datamatpending = FXCollections.observableArrayList();
        
        pendingmaterialtender.setDisable(true);
        pendingtendermaterialtype.setDisable(true);
       
        
        pendingsetTenderMaterialTable();
        pendingloadFromTenderMaterialDB();
        pendingsetMaterialTable();
        pendingloadMaterialDB();
        RowclickEvent14();
        RowclickEvent15();
        search();
    
    } 
    
     public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();
     }
      private void pendingsetTenderMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pendingmatTendType.setCellValueFactory(new PropertyValueFactory<>("matType"));
            pendingmatReq.setCellValueFactory(new PropertyValueFactory<>("reqCount"));
            pendingmatAssign.setCellValueFactory(new PropertyValueFactory<>("assignCount"));
        } catch (Exception e) {
            System.out.println("ranjithatendermatset");
            System.err.println("Error set table data " + e);
        }

    }

    private void pendingloadFromTenderMaterialDB() {

        dataMatTendpending.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,materialType,materialcount,assignCount from materialtender WHERE tenderId IN (SELECT tenderId from equiptender WHERE assignCount>0 and tenderId IN (select tenderId from tender where status='pending')) ");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataMatTendpending.add(new matTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                //  dataKISH.add(new KISHANTH(null, null, null));
            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        pendingtendMatTbl.setItems(dataMatTendpending);
    }

    private void pendingsetMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingmattype.setCellValueFactory(new PropertyValueFactory<>("matType"));
            pendingmatcount.setCellValueFactory(new PropertyValueFactory<>("available"));

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error set table data " + e);
        }

    }

    private void pendingloadMaterialDB() {

        datamatpending.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select type,quantity from rawmaterial where quantity>0");
            rs = pst.executeQuery();

            while (rs.next()) {
                datamatpending.add(new Material(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        pendingmaterial.setItems(datamatpending);

    }

    @FXML
    public void pendingaddTenderMaterial() throws SQLException {

        try {

            String addMatTender = pendingmaterialtender.getText();
            String addMaterial = pendingtendermaterialtype.getText();
            Integer addMatcount= Integer.parseInt(pendingMatCount.getText());

            if (addMatTender.isEmpty() || addMaterial.isEmpty()) {
                 alerboxInfo("Operation Failed","Fields cannot be empty");
                  pendingmaterialtender.clear();
                 pendingtendermaterialtype.clear();
                 pendingMatCount.clear();
               
            }
            
             else if(addMatcount<=0){
                 alerboxInfo("Operation Failed","you have enterd 0 or lesser value for count ");
            }
             else{
             PreparedStatement stmt2 = con.prepareStatement("select materialCount from materialtender where tenderid=? and materialType=?");
           stmt2.setString(1, addMatTender); 
           stmt2.setString(2, addMaterial);
           rs = stmt2.executeQuery();

            while (rs.next()) {
                dataMatTendpending.add(new matTender( rs.getInt(1)));
                 Integer amount;
                amount=rs.getInt(1);
                
                if (amount<addMatcount){
                 System.out.println("amount is"+amount);
                 System.out.println("amount is less");
                 
                 alerboxInfo("Error","Assigning amount cannot be greater than required amount. Try entering again");
                pendingMatCount.clear();
                
            }

                else{
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("UPDATE materialtender SET assignCount=assignCount+? where tenderId=? and materialType=?");
            stmt.setInt(1, addMatcount);
            stmt.setString(2, addMatTender);
            stmt.setString(3, addMaterial);
            stmt.executeUpdate();
            System.out.println("success adding material");
             alerboxInfo("operation SuucessFull","Material Added Successfully");

           

            PreparedStatement stmt1 = con4.prepareStatement("UPDATE rawmaterial SET quantity = quantity-? WHERE type =?");
            stmt1.setInt(1, addMatcount);
            stmt1.setString(1, addMaterial);
            stmt1.executeUpdate();
            
            PreparedStatement stmt3 = con4.prepareStatement("UPDATE tender SET status ='on progress' WHERE tenderId =?");
            stmt3.setString(1, addMatTender);
           
            stmt3.executeUpdate();
            

             pendingloadFromTenderMaterialDB();
            pendingloadMaterialDB();
                }
            }
            }
        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error adding Material ");
            alert.show();
        }
        pendingmaterialtender.clear();
        pendingtendermaterialtype.clear();
    }
    
     private void RowclickEvent14() {
        pendingtendMatTbl.setOnMouseClicked((e)
                -> {
            matTender v1 = (matTender) pendingtendMatTbl.getItems().get(pendingtendMatTbl.getSelectionModel().getSelectedIndex());
            pendingmaterialtender.setText(v1.getMatTender());
            pendingtendermaterialtype.setText(v1.getMatType());
            
            datamatpending.clear();
            try{
            String addTender = pendingmaterialtender.getText();
            String mType = pendingtendermaterialtype.getText();
                
             PreparedStatement stmt1 = con.prepareStatement("select type,quantity from rawmaterial WHERE type =?");
            stmt1.setString(1, mType);
            rs=stmt1.executeQuery();  
            
            while(rs.next()){
            datamatpending.add(new Material(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e1) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data ");

        }
        pendingmaterial.setItems(datamatpending);
            
        });
    }

    private void RowclickEvent15() {
        pendingmaterial.setOnMouseClicked((e)
                -> {
            Material v1 = (Material) pendingmaterial.getItems().get(pendingmaterial.getSelectionModel().getSelectedIndex());
            pendingtendermaterialtype.setText(v1.getMatType());

        });
    }
    
    
     @FXML
     private void backClicked(ActionEvent event) throws IOException {
      dkjconstruction.DKJConstruction.showJobAllocation();
        
       
    }
     
      public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
               // loadFromTenderDB();
                //loadFromAssetDB();
               // loadFromJobEquipDB();
              //  loadFromJobAssetADB();
              pendingloadFromTenderMaterialDB();
            } else {
                dataMatTendpending.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("select tenderId,materialType,materialcount,assignCount from materialtender WHERE tenderId IN (SELECT tenderId from equiptender WHERE assignCount>0) and tenderId  LIKE '%" + searchfield.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        dataMatTendpending.add(new matTender(rs.getString(1),rs.getString(2), rs.getInt(3), rs.getInt(4)));
                    }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                pendingtendMatTbl.setItems(dataMatTendpending);

            }

        });//event
    }
    
}
