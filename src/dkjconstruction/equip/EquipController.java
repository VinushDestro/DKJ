/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import dkjconstruction.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import dkjconstruction.equip.Equipment;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
//import dkj.assets.management.Equipment.EquipmentDetail;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static org.apache.xalan.xsltc.compiler.util.Type.Int;

/**
 * FXML Controller class
 *
 * @author VINUSH
 */
public class EquipController implements Initializable {

    @FXML
    TableView<EquipmentDetail> equipTab;
    @FXML
    TableColumn tabName;
//    @FXML
//    TableColumn tabID;
    @FXML
    TableColumn tabCount;
    @FXML
    TableColumn tabAssignedCount;
  
    @FXML
    TableColumn tabCost;
    @FXML
    TableColumn tabTotalCost;

    @FXML
    private JFXComboBox view;
    @FXML
    private JFXTextField equipName;
    @FXML
    private TextField search;
    @FXML
    private JFXTextField cost;
    @FXML
    private JFXTextField count;
    private ObservableList<EquipmentDetail> equipmentSe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        view.getItems().addAll("View_All", "Assigned");
        view.getSelectionModel().selectFirst();

        loadTable();
        RowclickEvent();
        doSearchVehicle();

    }

    @FXML
    public void doAddEquipment() throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipment Added");
        alert.setHeaderText(null);
        try {

            int result = 0;
            String name = equipName.getText().trim();
            //  String equipID = eID.getText().trim();
            // Double cst = Double.parseDouble(cost.getText().trim());
            String cst = cost.getText().trim();
            //int cut= Integer.parseInt(count.getText().trim());
            String cut = count.getText().trim();
            // String tCost = totalCost.getText().trim();

            if (name.isEmpty() || cost.getText().trim().isEmpty() || count.getText().trim().isEmpty()) {
                alert.setContentText("Fields cannot be empty");
            } else if (Double.valueOf(cst) <= 0) {
                alert.setContentText("Cost cannot be 0");
            } else if (Integer.valueOf(cut) <= 0) {
                alert.setContentText("Count cannot be 0");
            } else {
                result = Equipment.addEquipment(equipName.getText(), Integer.parseInt(count.getText()), Double.parseDouble(cost.getText()));
                acc.mn();
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            }

        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Operation Failed");
            alert.show();

        } finally {
            alert.show();

           doClear();
        }

        try {
            updateLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void doUpdateEquipment() throws SQLException, ClassNotFoundException {
        int result = 0;
        String name = equipName.getText().trim();

        String cut = count.getText().trim();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Equipment");
        alert.setHeaderText(null);
 try {
        if (name.isEmpty() || cut.isEmpty()) {

            alert.setContentText("Fields cannot be empty");
        } else if (Integer.valueOf(cut) <= 0) {
            alert.setContentText("Cannot update to Zero or Less than!");
        } else if (name.isEmpty()) {
            alert.setContentText("Equipment Name field cannot be empty");
        } else if (cut.isEmpty()) {
            alert.setContentText("Count field cannot be empty");
        } else {
            try {
                result = Equipment.updateEquipment(String.valueOf(name), cut); //Integer.valueOf(cut)
                acc.addE(equipName.getText(), Integer.parseInt(count.getText()), Double.parseDouble(cost.getText()));
                if (result == 1) {
                    alert.setContentText("Operation AddCount Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            } catch (NumberFormatException e1) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText(e1.getMessage() + "\nOperation Failed");
                alert2.show();
            }
        }

        alert.show();
        doClear();

       
            updateLoad();
        } catch (Exception e) {
           // e.printStackTrace();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText("Invalid Object Count");
                alert2.show();
        }

    }

    @FXML
    private void doDeleteEquipment() throws SQLException, ClassNotFoundException {
        String s = equipName.getText();
        s = s.trim();
       String cut = count.getText().trim();
      
       try {
           if(Equipment.checkDelete(equipName.getText())){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Equipment");
            alert.setHeaderText(null);
            alert.setContentText("Assigned Equipment Can't be Deleted");
            alert.show();
           }
           
       else if (!s.isEmpty() && count.getText().trim().isEmpty() ) {
            int result = 0;
            result = Equipment.deleteEquipment(s);

            equipName.clear();
            count.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Equipment Successfully Deleted!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Equipment Not Found!");
                alert.show();
            }
        } else if (s.isEmpty() && !count.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Equipment");
            alert.setHeaderText(null);
            alert.setContentText(" Name required to Delete!");
            alert.show();
        }else if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Equipment");
            alert.setHeaderText(null);
            alert.setContentText(" Name Cannot be Empty!");
            alert.show();
        }else if (!s.isEmpty() && (Integer.valueOf(cut) <= 0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Equipment");
            alert.setHeaderText(null);
            alert.setContentText(" Count not less than 0!");
            alert.show();
        }  
        else if (!s.isEmpty() && !count.getText().trim().isEmpty()) {
           // int c = Integer.parseInt(count.getText().trim());
            int result = 0;
            result = Equipment.deleteEquipment(s, cut);

            equipName.clear();
            count.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Equipment Successfully Deleted!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Equipment Not Found!");
                alert.show();
            }
        }
        
            updateLoad();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Invalid Entry!");
                alert.show();
        }
        doClear();
        
    }

    private void updateLoad() throws IOException, ClassNotFoundException, SQLException {
        if ((String) view.getValue() == "View_All") {
            loadTable();
        } else if ((String) view.getValue() == "Available") {
            equipTab.setItems(Equipment.getAssignedEquipment());
        }
    }

    public void loadTable() {
        tabName.setCellValueFactory(new PropertyValueFactory<EquipmentDetail, String>("Name"));
        tabCount.setCellValueFactory(new PropertyValueFactory<EquipmentDetail, Integer>("Count"));
        tabCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail, Double>("Cost"));
        tabTotalCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail, Double>("TotalCost"));
        tabAssignedCount.setCellValueFactory(new PropertyValueFactory<EquipmentDetail, Double>("AssignedCount"));
        
        try {
            equipTab.setItems(Equipment.getEquipment());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void doView() {
        try {
            if ((String) view.getValue() == "Assigned") {
                equipTab.setItems(Equipment.getAssignedEquipment());
                search.clear();
            } else if ((String) view.getValue() == "View_All") {
                equipTab.setItems(Equipment.getEquipment());
                search.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RowclickEvent() {
        equipTab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    EquipmentDetail tab = equipTab.getItems().get(equipTab.getSelectionModel().getSelectedIndex());

                    equipName.setText(tab.getName());
                    equipName.setDisable(true);
                    // eID.setText(tab.getEquipID());

                    count.setText(tab.getCount());

                    cost.setText(tab.getCost());
                    cost.setDisable(true);

                } catch (Exception ex) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText("No row is clicked");
                    alert2.show();
                }

            }
        });
    }

    private void doSearchVehicle() {
        search.setOnKeyReleased(e -> {

            try {
                Connection con = DbConnection.getConnection();

                if ((String) view.getValue() == "View_All") {

                    PreparedStatement pst = con.prepareStatement("select * from equipment where name like '%" + search.getText() + "%'"
                            + "union select * from equipment where count like '%" + search.getText() + "%'");

                    ResultSet rs = pst.executeQuery();
                    equipmentSe = FXCollections.observableArrayList();

                    while (rs.next()) {

                        String name = rs.getString("name");
            String cost = rs.getString("cost");
            String count = rs.getString("Count");
            double totalCost = rs.getDouble("totalCost");
            int assignedCount=rs.getInt("assignedCount");
            
                        equipmentSe.add(new EquipmentDetail(name, count, cost, totalCost,assignedCount));
                    }
                    equipTab.setItems(equipmentSe);
                } else if ((String) view.getValue() == "Assigned") {

                    PreparedStatement pst = con.prepareStatement("select * from equipment where name like '%" + search.getText() + "%' and assignedCount>0"
                           + "union select * from equipment where count like '%" + search.getText() + "%' and assignedCount>0");

                    ResultSet rs = pst.executeQuery();
                    equipmentSe = FXCollections.observableArrayList();

                    while (rs.next()) {

                        String name = rs.getString("name");
                        String cost = rs.getString("cost");
                        String count = rs.getString("Count");
                        double totalCost = rs.getDouble("totalCost");
                        int assignedCount=rs.getInt("assignedCount");
           
                        equipmentSe.add(new EquipmentDetail(name, count, cost, totalCost,assignedCount));
                    }
                    equipTab.setItems(equipmentSe);
                } else if (search.getText().equals("")) {
                    updateLoad();
                }

            } catch (SQLException ex) {
                System.err.println("Error loading table data " + ex);

            } catch (IOException ex) {
                Logger.getLogger(EquipController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EquipController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // }
        });
    }

    @FXML
    public void doClear() {
        equipName.clear();
        equipName.setDisable(false);
        count.clear();
        cost.clear();
        cost.setDisable(false);
        search.clear();
    }

    @FXML
    private void showReport(){// throws JRException, SQLException, ClassNotFoundException {
     try{  
         if (view.getValue()=="View_All"){
             DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\equip\\All_Equip.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        DbConnection.closeConnection();
         }
         else if(view.getValue()=="Assigned"){
             DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\equip\\Assigned_Equip.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        DbConnection.closeConnection();
         }
     }
     catch (Exception ex){System.out.println("error"+ ex);}
    }
    
    
}
