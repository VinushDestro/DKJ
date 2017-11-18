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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 * FXML Controller class    
 *
 * @author Ranjitha
 */
public class MaterialAllocationController implements Initializable {
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    private ObservableList<matTender> dataMatTend;
    private ObservableList<Material> datamat;
    
    @FXML
    private TableView material;
    @FXML
    private TableColumn mattype;
    @FXML
    private TableColumn matcount;
    @FXML
    private JFXTextField materialtender;
    @FXML
    private JFXTextField tendermaterialtype;
    @FXML
    private JFXTextField matCount;
    @FXML
    private JFXTextField searchfield;
    
    @FXML
    private TableView tendMatTbl;
    @FXML
    private TableColumn matTendID;
    @FXML
    private TableColumn matTendType;
    @FXML
    private TableColumn matReq;
    @FXML
    private TableColumn matAssign;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataMatTend = FXCollections.observableArrayList();
        datamat = FXCollections.observableArrayList();
        setTenderMaterialTable();
        loadFromTenderMaterialDB();
        setMaterialTable();
        loadMaterialDB();
         RowclickEvent6();
        RowclickEvent7();
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

     private void setTenderMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            matTendID.setCellValueFactory(new PropertyValueFactory<>("matTender"));
            matTendType.setCellValueFactory(new PropertyValueFactory<>("matType"));
            matReq.setCellValueFactory(new PropertyValueFactory<>("reqCount"));
            matAssign.setCellValueFactory(new PropertyValueFactory<>("assignCount"));
        } catch (Exception e) {
            System.out.println("ranjithatendermatset");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromTenderMaterialDB() {

        dataMatTend.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,materialType,materialcount,assignCount from materialtender");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataMatTend.add(new matTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                //  dataKISH.add(new KISHANTH(null, null, null));
            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        tendMatTbl.setItems(dataMatTend);
    }

    private void setMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            mattype.setCellValueFactory(new PropertyValueFactory<>("matType"));
            matcount.setCellValueFactory(new PropertyValueFactory<>("available"));

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadMaterialDB() {

        datamat.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select type,quantity from rawmaterial");
            rs = pst.executeQuery();

            while (rs.next()) {
                datamat.add(new Material(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        material.setItems(datamat);

    }

    @FXML
    public void addTenderMaterial() throws SQLException {

        try {

            String addMatTender = materialtender.getText();
            String addMaterial = tendermaterialtype.getText();
            Integer addMatcount= Integer.parseInt(matCount.getText());
           
            
            if (addMatTender.isEmpty() || addMaterial.isEmpty()) {
               
                alerboxInfo("Operation Failed","Fields cannot be empty");
              
            }
            else if(addMatcount==0){
                 alerboxInfo("Operation Failed","You have enterd 0 for count ");
            }
            
            PreparedStatement stmt2 = con.prepareStatement("select assignCount from materialtender where tenderid=? and materialType=?");
           stmt2.setString(1, addMatTender); 
           stmt2.setString(2, addMaterial);
           rs = stmt2.executeQuery();
           
              while (rs.next()) {
                dataMatTend.add(new matTender( rs.getInt(1)));
                //  dataKISH.add(new KISHANTH(null, null, null));
                 Integer amount;
                amount=rs.getInt(1);
                
                if (amount>addMatcount){
                 System.out.println("amount is"+amount);
                 System.out.println("amount is less");
                 
                 alerboxInfo("Attention","Assigning amount greater than required");
                
            }
            
            
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("UPDATE materialtender SET assignCount=assignCount+? where tenderId=? and materialType=?");
            stmt.setInt(1, addMatcount);
            stmt.setString(2, addMatTender);
            stmt.setString(3, addMaterial);
            stmt.executeUpdate();
           
          
            PreparedStatement stmt1 = con4.prepareStatement("UPDATE rawmaterial SET quantity = quantity-? WHERE type =?");
            stmt1.setInt(1, addMatcount);
            stmt1.setString(2, addMaterial);
            stmt1.executeUpdate();
             System.out.println("success adding material");

            alerboxInfo("Operation success","Material Added Successfully");

            loadFromTenderMaterialDB();
            loadMaterialDB();
              }
       
           
        } catch (Exception e) {

            System.out.println("error" + e);
            alerboxInfo("Operation Failed","error adding Material"+e);
          
        }
        materialtender.clear();
        tendermaterialtype.clear();

    }
    
     private void RowclickEvent6() {
        tendMatTbl.setOnMouseClicked((e)
                -> {
            matTender v1 = (matTender) tendMatTbl.getItems().get(tendMatTbl.getSelectionModel().getSelectedIndex());
            materialtender.setText(v1.getMatTender());

        });
    }

    private void RowclickEvent7() {
        material.setOnMouseClicked((e)
                -> {
            Material v1 = (Material) material.getItems().get(material.getSelectionModel().getSelectedIndex());
            tendermaterialtype.setText(v1.getMatType());

        });
    }
    
    
     @FXML
     private void nextClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pendingMaterial.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        
    }
     
    
     public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
               // loadFromTenderDB();
                //loadFromAssetDB();
               // loadFromJobEquipDB();
              //  loadFromJobAssetADB();
                loadFromTenderMaterialDB();
            } else {
                dataMatTend.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("select tenderId,materialType,materialcount,assignCount from materialtender where tenderId  LIKE '%" + searchfield.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        dataMatTend.add(new matTender(rs.getString(1),rs.getString(2), rs.getInt(3), rs.getInt(4)));
                    }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                tendMatTbl.setItems(dataMatTend);

            }

        });//event
    }

    @FXML
    private void tender_report(ActionEvent event) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            String report = "C:\\Users\\Ranjitha\\Documents\\NetBeansProjects\\DKJ construction\\src\\dkj\\construction\\joballocation\\MA.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        } catch (Exception e) {
            System.out.println("sdas");
        }
    }
    
    
}
