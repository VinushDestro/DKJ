/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.rawmaterial;

import java.net.URL;
import java.sql.ResultSet;
import dkjconstruction.DbConnection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import static dkjconstruction.rawmaterial.RawMaterial.getRawmaterial;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class RawMaterialsController implements Initializable {

    @FXML
    TableView<dkjconstruction.rawmaterial.RawMaterialDetail> rawmaterialTab;
    @FXML
    TableColumn tabType;
    @FXML
    TableColumn tabQuantity;
    @FXML
    TableColumn tabPrice;
    @FXML
    TableColumn tabSupplier;
    @FXML
    TableColumn tabMeasurement;

    @FXML
    private JFXTextField Type;

    @FXML
    private TextField searchr;

    @FXML
    private JFXTextField Quantity;

    @FXML
    private JFXTextField Price;

    @FXML
    private JFXTextField Supplier;

    @FXML
    private JFXTextField Measurement;

    @FXML
    Button Add;

    @FXML
    Button Update;

    @FXML
    Button Delete;

    @FXML
    Button clear;
    
     @FXML
    Button deduct;
     
       @FXML
    Button report;
    
    

    private ObservableList<RawMaterialDetail> getRawMaterial;
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        loadTable();
//        doSearchRawMaterial();
//        RowclickEvent();         
//    }    

    @FXML
    private void doclearRawmaterial() throws SQLException, ClassNotFoundException {
        Type.clear();
        Quantity.clear();
        Price.clear();
        Supplier.clear();
        Measurement.clear();
        Type.setDisable(false);
         Measurement.setDisable(false);
           Price.setDisable(false);
           Supplier.setDisable(false);
    }

    public void loadTable() {
        tabType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        tabPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tabSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        tabMeasurement.setCellValueFactory(new PropertyValueFactory<>("Measurement"));

        try {
            rawmaterialTab.setItems(RawMaterial.getRawmaterial());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void doAddRawmaterial() throws SQLException, ClassNotFoundException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION );
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        try {
            if (Type.getText().isEmpty() || Price.getText().isEmpty() || Quantity.getText().isEmpty() || Supplier.getText().isEmpty() || Measurement.getText().isEmpty()) {
                alert.setContentText("All Fields cannot be empty");
                alert.showAndWait();
             
            } else {
                int result = 0;
                String type = Type.getText().trim();
                double price = Double.parseDouble(Price.getText().trim());
                int quantity = Integer.parseInt(Quantity.getText().trim());
                String measurement = Measurement.getText().trim();
                int supplier = Integer.parseInt(Supplier.getText().trim());
                result = dkjconstruction.rawmaterial.RawMaterial.addrawmaterial(type, quantity, price, measurement, supplier);
                accraw.mnq();
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                    alert.showAndWait();
                     loadTable();
                   doclearRawmaterial() ;
                    
                } else {
                    alert.setContentText("Operation Failed");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("error in program" + e);

            alert.setContentText("You have entered an existing keyword"+e);
             alert.showAndWait();

        }
        
        

    }

    @FXML
    private void doUpdateRawmaterial(ActionEvent event) throws SQLException, ClassNotFoundException {
       
       

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        if (Type.getText().trim().isEmpty() || Price.getText().trim().isEmpty() || Quantity.getText().trim().isEmpty() || Supplier.getText().trim().isEmpty() || Measurement.getText().trim().isEmpty()) {
            alert.setContentText("All Fields cannot be empty");
              alert.showAndWait();
          } else {
            try { int result = 0;
        String type = Type.getText().trim();
        double price = Double.parseDouble(Price.getText().trim());
        int quantity = Integer.parseInt(Quantity.getText().trim());
        String measurement = Measurement.getText().trim();
        int supplier = Integer.parseInt(Supplier.getText().trim());
        Type.setDisable(false);
                result = dkjconstruction.rawmaterial.RawMaterial.updaterawmaterial( type,quantity);
                 accraw.addEOQ(type, price, quantity);
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                     alert.showAndWait();
                     loadTable();
                    // Type.setDisable(false);
                    doclearRawmaterial() ;
                } else {
                    alert.setContentText("Operation Failed");
                     alert.showAndWait();

                }
            } catch (NumberFormatException e1) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText(e1.getMessage() + "\nOperation Failed");
                alert2.show();
            }
        }

    

    }

    
    
    
    @FXML
    private void doDeleteRawmaterial() throws SQLException, ClassNotFoundException {
        String s = Type.getText();
        s = s.trim();

        if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Rawmaterial");
            alert.setHeaderText(null);
            alert.setContentText("Raw Material Field Cannot be Empty!");
            alert.show();
             alert.showAndWait();}
           else if (RawMaterial.doCheckAvailble(Type.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText("Assigned RawMaterial Can't be Deleted");
            alert.show();
        }  else {
            int result = 0;
            result = dkjconstruction.rawmaterial.RawMaterial.deleterawmaterial(Type.getText());

      //      Type.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Rawmaterial");
                alert.setHeaderText(null);
                alert.setContentText("Rawmaterial Successfully Deleted!");
          //      alert.show();
                 alert.showAndWait();
                     loadTable();
                     Type.setDisable(false);
                     doclearRawmaterial();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Ramwaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Rawmaterial Not Found!");
                alert.show();
            }
             
            
        }
       

        
    }
    
    
    
    
    
    
    
     @FXML
    private void deductmaterial(ActionEvent event) throws SQLException, ClassNotFoundException {
       
       

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        if (Type.getText().trim().isEmpty() || Price.getText().trim().isEmpty() || Quantity.getText().trim().isEmpty() || Supplier.getText().trim().isEmpty() || Measurement.getText().trim().isEmpty()) {
            alert.setContentText("All Fields cannot be empty");
              alert.showAndWait();
          } else {
            try { int result = 0;
        String type = Type.getText().trim();
        double price = Double.parseDouble(Price.getText().trim());
        int quantity = Integer.parseInt(Quantity.getText().trim());
        String measurement = Measurement.getText().trim();
        int supplier = Integer.parseInt(Supplier.getText().trim());
        Type.setDisable(false);
                result = dkjconstruction.rawmaterial.RawMaterial.deductmaterial( type,quantity);
                 
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                     alert.showAndWait();
                     loadTable();
                    // Type.setDisable(false);
                    doclearRawmaterial() ;
                } else {
                    alert.setContentText("Operation Failed");
                     alert.showAndWait();

                }
            } catch (NumberFormatException e1) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText(e1.getMessage() + "\nOperation Failed");
                alert2.show();
            }
        }

    

    }

    
    
    
    
    
    /* @FXML
    public void doDeleteRawmaterial() throws SQLException, ClassNotFoundException {
        String s = Type.getText();
        s = s.trim();

        if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText("Raw Material Field Cannot be Empty!");
            alert.show();
        } else if (RawMaterial.doCheckAvailble(Type.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText("Assigned RawMaterial Can't be Deleted");
            alert.show();
        } else {
            int result = 0;
            if (RawMaterial.doCheckAvailble(Type.getText())) {
                System.out.println("CHECKING");
            }

            result = RawMaterial.deleterawmaterial( Type.getText());

            Type.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("RawMaterial Successfully Deleted!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n RawMaterial Not Found!");
                alert.show();
            }

        }
        try {
            doClear();
            updateLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    */
    
    
    /*
        @FXML
    private void doDeleteRawmaterial() throws SQLException, ClassNotFoundException {
        String s = Type.getText().trim();
    
      int cut = Integer.parseInt(Quantity.getText().trim());
      //  int  cut = Quantity.getText().trim();
      
       try { 
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        if (Type.getText().isEmpty()) {
          //  Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);
            alert.setContentText("Type cannot be empty");
              alert.showAndWait();}
          else if(RawMaterial.doCheckAvailble(Type.getText())){
           //    Alert alert = new Alert(Alert.AlertType.INFORMATION); 
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText("Assigned RawMaterial Can't be Deleted");
            alert.show();
           }
           
       else if (!s.isEmpty() && Quantity.getText().trim().isEmpty() ) {
            int result = 0;
            result = RawMaterial.deleterawmaterial(s);

            Type.clear();
            Quantity.clear();
            if (result == 1) {
         //       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("RawMaterial Successfully Deleted!");
                alert.show();
            } else if (result == 0) {
           //     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n RawMaterial Not Found!");
                alert.show();
            }
        } else if (s.isEmpty() && !Quantity.getText().trim().isEmpty()) {
         //   Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText(" name required to Delete!");
            alert.show();
        }else if (s.isEmpty()) {
          //  Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText(" Name Cannot be Empty!");
            alert.show();
        }else if (!s.isEmpty() && (Integer.valueOf(Quantity.getText()) <= 0)) {
         //   Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete RawMaterial");
            alert.setHeaderText(null);
            alert.setContentText(" Count not less than 0!");
            alert.show();
        }  
        else if (!s.isEmpty() && (Quantity.getText().isEmpty())) {
           // int c = Integer.parseInt(count.getText().trim());
            int result = 0;
            result = RawMaterial.deleterawmaterial(s,cut);

          Type.clear();
            Quantity.clear();
            if (result == 1) {
        //        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("RawMaterial Successfully Deleted!");
                alert.show();
                   loadTable();
                   
                    doclearRawmaterial() ;
            } else if (result == 0) {
         //       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n RawMaterial Not Found!");
                alert.show();
            }
        }
        
           loadTable();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete RawMaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Invalid Entry!");
                alert.show();
        }
       
        
    }
*/
       

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tabPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tabMeasurement.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        tabSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        try {
            rawmaterialTab.setItems(RawMaterial.getRawmaterial());
        } catch (IOException ex) {
            Logger.getLogger(RawMaterialsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RawMaterialsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RawMaterialsController.class.getName()).log(Level.SEVERE, null, ex);
        }

       doSearchRawMaterial();
        RowclickEvent();
         loadTable();

    }

    //rowclick for rawmaterialtable
    @FXML
    public void RowclickEvent() {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RowclickEvent");
        alert.setHeaderText(null);
     rawmaterialTab.setOnMouseClicked((MouseEvent e) -> {
            try{  
            RawMaterialDetail r1 = (RawMaterialDetail) rawmaterialTab.getItems().get(rawmaterialTab.getSelectionModel().getSelectedIndex());

            Type.setText(r1.getType());
            Quantity.setText(String.valueOf(r1.getQuantity()));
            Price.setText(String.valueOf(r1.getPrice()));
            Measurement.setText(String.valueOf(r1.getMeasurement()));
            Supplier.setText(String.valueOf(r1.getSupplier()));
            
           Type.setDisable(true);
         Measurement.setDisable(true);
        Price.setDisable(true);
            Supplier.setDisable(true);
          
          
            
         
                
                 }catch (ArrayIndexOutOfBoundsException ex) {
           
            alert.setContentText("no row is selected");
                    alert.showAndWait();
                 
                 }

    });}

    /*
@FXML    
public void doSearchRawMaterial() {
        search.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (search.getText().equals("")) {
                    
                    
                    tabType.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,String>("type"));
                    tabQuantity.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,Integer>("quantity"));
                    tabPrice.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,Double>("price"));
                    tabMeasurement.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,Double>("measurement"));
                    tabSupplier.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,Double>("supplierId"));
                    
                    
                    
                    
                    
                    try {
                        rawmaterialTab.setItems(RawMaterial.getRawmaterial());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                }
                else{
                    try {
                        Connection con = DbConnection.getConnection();
                        
                        PreparedStatement pst = con.prepareStatement
                                     ("select * from rawmaterial where name like '%" + search.getText() + "%'"
                                             + "union select * from rawmaterial where count like '%" + search.getText() + "%'");
                        
                        
                        ResultSet rs = pst.executeQuery();
                        rawmaterialTab.setItems(RawMaterial.getRawmaterial());
                        
                        while(rs.next()) {
                            
                            String type=rs.getString("type");
                            int quantity=rs.getInt("quantity");
                            Double price=rs.getDouble("price");
                            int supplier=rs.getInt("supplierId");
                            String measurement=rs.getString("measurement");
                            
                             getRawMaterial.add(new RawMaterialDetail(type,quantity,price,measurement,supplier));
                        }
                        rawmaterialTab.setItems(getRawMaterial);
                        
                    } catch (SQLException ex) {
                        System.err.println("Error loading table data " + ex);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(RawMaterialsController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RawMaterialsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
    }
     */
 
    public void doSearchRawMaterial() {
        searchr.setOnKeyReleased(e -> {
            if (searchr.getText().equals("")) {

                tabType.setCellValueFactory(new PropertyValueFactory<>("type"));
                tabQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                tabPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                tabMeasurement.setCellValueFactory(new PropertyValueFactory<>("measurement"));
                tabSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

                try {

                    rawmaterialTab.setItems(RawMaterial.getRawmaterial());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                try {
                    Connection con = DbConnection.getConnection();

                    PreparedStatement pst = con.prepareStatement("select * from rawmaterial where type like '%" + searchr.getText() + "%'"
                            + "union select * from rawmaterial where quantity like '%" + searchr.getText() + "%'");

                    ResultSet rs = pst.executeQuery();
                    getRawMaterial = FXCollections.observableArrayList();

                    while (rs.next()) {

                        String type = rs.getString("type");
                        int quantity = rs.getInt("quantity");
                        Double price = rs.getDouble("price");
                        int supplier = rs.getInt("supplierId");
                        String measurement = rs.getString("measurement");

                        getRawMaterial.add(new RawMaterialDetail(type, quantity, price, measurement, supplier));

                    }
                    rawmaterialTab.setItems(getRawMaterial);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }

            }
        });
    }


    
    @FXML
    private void showReportRawMaterial(){// throws JRException, SQLException, ClassNotFoundException {
     try{   DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\dkjconstruction\\src\\dkjconstruction\\rawmaterial\\RawMaterial.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        
     }
     catch (Exception ex){System.out.println("error"+ ex);}
    }


}



