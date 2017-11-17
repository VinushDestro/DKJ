package dkjconstruction.vehicle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import static com.sun.deploy.panel.JreFindDialog.search;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import dkjconstruction.vehicle.VehicleManagement;
import dkjconstruction.vehicle.VehicleDetail;
import static dkjconstruction.vehicle.VehicleManagement.getVehicle;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import static jdk.nashorn.internal.objects.NativeString.search;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author VINUSH
 */
public class VehicleController implements Initializable {

    @FXML
    TableView<VehicleDetail> vehicleTab;
    @FXML
    TableColumn tabName;
    @FXML
    TableColumn tabRegNo;
    @FXML
    TableColumn tabType;
    @FXML
    TableColumn tabCost;
    @FXML
    TableColumn tabLifeTime;
    @FXML
    TableColumn tabBoughtDate;
    @FXML
    TableColumn tabCondition;
    @FXML
    TableColumn tabCurrentDep;
    @FXML
    TableColumn tabDepPercent;
    @FXML
    TableColumn tabTotalDep;
    @FXML
    TableColumn tabCurrentValue;

    @FXML
    private TextField search;

    @FXML
    private JFXTextField vehName;

    @FXML
    private JFXTextField dep;

    @FXML
    private JFXTextField regNo;

    @FXML
    private JFXTextField liTime;

    @FXML
    private JFXTextField cost;

    @FXML
    private JFXDatePicker bouDate;

//    @FXML
//    private JFXTextField condi;
    @FXML
    private Button rep;

    @FXML
    private Button avai;
    
    @FXML
    private Button del;

    @FXML
    private JFXComboBox type;

    @FXML
    private JFXComboBox condi;

    @FXML
    private JFXComboBox view;

    private ObservableList<VehicleDetail> vehicleSe;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type.getItems().addAll("Heavy", "Light", "Transport");
        condi.getItems().addAll("Brand New", "Used", "Refurbished", "Damage", "Old");
        view.getItems().addAll("View_All", "Available","Assigned", "Repair");
        view.getSelectionModel().selectFirst();
        rep.setVisible(false);
        avai.setVisible(false);

        LoadTable();
        doSearchVehicle();
        RowclickEvent();
        //  System.out.println(search.getText());

    }

    @FXML
    private void doAddVehicle() throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle Added");
        alert.setHeaderText(null);
        try {
            int result = 0;

            String name = vehName.getText().trim();
            String rNo = regNo.getText().trim();
            String typ = type.getPromptText().trim();
            //  Double cst = Double.parseDouble(cost.getText().trim());
            String cst = cost.getText().trim();

            LocalDate bDate = bouDate.getValue();
            //  int lifT=Integer.parseInt(liTime.getText().trim());
            String lifT = liTime.getText().trim();
            String cond = condi.getPromptText().trim();
            String calc = dep.getText().trim();

            if (name.isEmpty() || rNo.isEmpty() || typ.isEmpty() || cst.isEmpty() || bDate.toString().isEmpty() || lifT.isEmpty() || cond.isEmpty() || calc.isEmpty()) {
                alert.setContentText("Fields cannot be empty");
            } else if (Double.valueOf(cst) <= 0) {
                alert.setContentText("Cost cannot be 0");
            } else if (Integer.valueOf(lifT) <= 0) {
                alert.setContentText("Life Time cannot be 0");
            } else {
                LocalDate today = LocalDate.now();
                if (bDate.isAfter(today)) {
                    alert.setContentText("Invalid value for date.\nShould be less than current date.");
                } else {
                    result = VehicleManagement.addVehicle(regNo.getText(), vehName.getText(), type.getValue().toString(), Double.parseDouble(cost.getText()), Date.valueOf(bDate), Integer.parseInt(liTime.getText()), (String) condi.getValue(), Float.parseFloat(dep.getText()));
                    // regno,name,type,cost,boughtDate,lifeTime,condition

                    if (result == 1) {
                        alert.setContentText("Operation Successful!");
                    } else {
                        alert.setContentText("Operation Failed");
                    }
                }
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Check your Fields");
            alert.show();

        }

        alert.show();
//        regNo.clear();
//        vehName.clear();
//        type.valueProperty().set(null);
//        cost.clear();
//        bouDate.getEditor().setText(null);
//        liTime.clear();
//        condi.valueProperty().set(null);
//        dep.clear();

        try {
            doClear();
            updateLoad();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void doUpdateVehicle(ActionEvent event) throws SQLException, ClassNotFoundException {

        int result = 0;

        String rNo = regNo.getText().trim();
        String lifT = liTime.getText().trim();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Vehicle");
        alert.setHeaderText(null);

        try {
            if (!rNo.isEmpty() && lifT.isEmpty() && ((String) condi.getValue() == null)) {
                alert.setContentText("You have to enter at least one field to update details!");
                System.out.println("step 1 regno only");
            } //  try{
            else if (rNo.isEmpty()) {
                alert.setContentText("Registration Number field cannot be empty");
                System.out.println("Step 2 regno empty");
            } else if (!rNo.isEmpty() && !lifT.isEmpty() && ((String) condi.getValue() == null)) {
                result = VehicleManagement.updateVehicle(String.valueOf(rNo), Integer.valueOf(lifT));
                if (result == 1) {
                    alert.setContentText("lifetime Successful!");
                } else if (result == 0) {
                    alert.setContentText("Operation Failed22222");
                }
            } else if (!rNo.isEmpty() && lifT.isEmpty() && !((String) condi.getValue() == null)) {
                result = VehicleManagement.updateVehicle(String.valueOf(rNo), (String) condi.getValue());
                if (result == 1) {
                    alert.setContentText("Condition Successful!");
                } else if (result == 0) {
                    alert.setContentText("Operation Failed33333");
                }
            } else if (!rNo.isEmpty() && !lifT.isEmpty() && !((String) condi.getValue() == null)) {
                result = VehicleManagement.updateVehicle(String.valueOf(rNo), Integer.valueOf(lifT), (String) condi.getValue());
                if (result == 1) {
                    alert.setContentText("Both Successful now edit!");
                } else if (result == 0) {
                    alert.setContentText("Operation Failed11111");
                }
            }

            alert.show();
            regNo.clear();
            liTime.clear();
            condi.valueProperty().set(null);
            doClear();

            updateLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void doDeleteVehicle() throws SQLException, ClassNotFoundException {
        String s = regNo.getText();
        s = s.trim();

        if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Vehicle");
            alert.setHeaderText(null);
            alert.setContentText("Registration Number Field Cannot be Empty!");
            alert.show();
        } else if (VehicleManagement.doCheckAvailble(regNo.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Vehicle");
            alert.setHeaderText(null);
            alert.setContentText("Assigned Vehicle Can't be Deleted");
            alert.show();
        } else {
            int result = 0;
            if (VehicleManagement.doCheckAvailble(regNo.getText())) {
                System.out.println("CHECKING");
            }

            result = VehicleManagement.deleteVehicle(regNo.getText());

            regNo.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Vehicle Successfully Deleted!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Vehicle Not Found!");
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

    //------------------------------------------------------VIEW
    @FXML
    public void doView() {

        try {  //String v = view.getPromptText().trim();
            if ((String) view.getValue() == "Repair") {
                vehicleTab.setItems(VehicleManagement.getRepairVehicle());
                rep.setVisible(false);
                avai.setVisible(true);
                 del.setVisible(true);
                 search.clear();
            } else if ((String) view.getValue() == "Available") {
                vehicleTab.setItems(VehicleManagement.getAvailableVehicle());
                avai.setVisible(false);
                rep.setVisible(true);
                 del.setVisible(true);
                 search.clear();
            } else if ((String) view.getValue() == "View_All") {
                vehicleTab.setItems(VehicleManagement.getVehicle());
                rep.setVisible(false);
                avai.setVisible(false);
                del.setVisible(true);
                search.clear();
            } else if ((String) view.getValue() == "Assigned"){
                vehicleTab.setItems(VehicleManagement.getAssignedVehicle());
                rep.setVisible(false);
                avai.setVisible(false);
                del.setVisible(false);
                search.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateLoad() throws IOException, ClassNotFoundException, SQLException {
        if ((String) view.getValue() == "View_All") {
            LoadTable();
        } else if ((String) view.getValue() == "Available") {
            vehicleTab.setItems(VehicleManagement.getAvailableVehicle());

        } else  if ((String) view.getValue() == "Repair"){
            vehicleTab.setItems(VehicleManagement.getRepairVehicle());
        }
        else{
            vehicleTab.setItems(VehicleManagement.getAssignedVehicle());
        }
    }

    @FXML
    public void doClear() {
        try {
            regNo.clear();
            vehName.clear();
            vehName.setDisable(false);
            type.valueProperty().set(null);
            // type.setVisible(true);
            type.setDisable(false);
            cost.clear();
            cost.setDisable(false);
            bouDate.getEditor().setText(null);
            bouDate.setDisable(false);
            liTime.clear();
            condi.valueProperty().set(null);
            dep.clear();
            dep.setDisable(false);
            search.clear();
            search.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void doRepairVehicle() throws SQLException, ClassNotFoundException {
        String s = regNo.getText();
        s = s.trim();

        if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Marked as Repair");
            alert.setHeaderText(null);
            alert.setContentText("Registration Number Field Cannot be Empty!");
            alert.show();
        } else {
            int result = 0;
            result = VehicleManagement.updateRepair(regNo.getText());

            regNo.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Marked as Repair");
                alert.setHeaderText(null);
                alert.setContentText("Vehicle Successfully Set!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Repair Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Failed! \n Vehicle Not Found!");
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

    @FXML
    public void doAvailable() throws SQLException, ClassNotFoundException {
        String s = regNo.getText();
        s = s.trim();

        if (s.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Marked as Available");
            alert.setHeaderText(null);
            alert.setContentText("Registration Number Field Cannot be Empty!");
            alert.show();
        } else {
            int result = 0;
            result = VehicleManagement.updateAvailable(regNo.getText());

            regNo.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Marked as Available");
                alert.setHeaderText(null);
                alert.setContentText("Vehicle Successfully Set!");
                alert.show();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Available Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Failed! \n Vehicle Not Found!");
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

    private void RowclickEvent() {
        vehicleTab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    VehicleDetail tab = vehicleTab.getItems().get(vehicleTab.getSelectionModel().getSelectedIndex());

                    vehName.setText(tab.getName());
                    //vehName.setEditable(false);
                    vehName.setDisable(true);

                    regNo.setText(tab.getRegNo());

                    type.setValue(tab.getType());
                    //type.setVisible(false);
                    type.setDisable(true);

                    cost.setText(tab.getCost());
                    cost.setDisable(true);

                    liTime.setText(tab.getLifeTime());

                    bouDate.setValue(LocalDate.parse(tab.getBoughtDate()));
                    bouDate.setDisable(true);

                    condi.setValue(tab.getCondition());

                    dep.setText(tab.getDepPercent());
                    dep.setDisable(true);
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

    private void LoadTable() {
        tabName.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("Name"));
        tabRegNo.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("RegNo"));
        tabType.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("Type"));
        tabCost.setCellValueFactory(new PropertyValueFactory<VehicleDetail, Double>("Cost"));
        tabLifeTime.setCellValueFactory(new PropertyValueFactory<VehicleDetail, Integer>("LifeTime"));
        tabBoughtDate.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("BoughtDate"));
        tabCondition.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("Condition"));
        tabCurrentDep.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("CurrentDep"));
        tabDepPercent.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("DepPercent"));
        tabTotalDep.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("TotalDep"));
        tabCurrentValue.setCellValueFactory(new PropertyValueFactory<VehicleDetail, String>("CurrentValue"));

        try {
            vehicleTab.setItems(VehicleManagement.getVehicle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doSearchVehicle() {
        search.setOnKeyReleased(e -> {

            try {
                Connection con = DbConnection.getConnection();

                if ((String) view.getValue() == "View_All") {
                    PreparedStatement pst = con.prepareStatement("select * from asset where name like '%" + search.getText() + "%'"
                            + "union select * from asset where regNo like '%" + search.getText() + "%'"
                            + "union select * from asset where type like '%" + search.getText() + "%'"
                            + "union select * from asset where cost like '%" + search.getText() + "%'"
                            + "union select * from asset where asset.condition like '%" + search.getText() + "%'");
                    ResultSet rs = pst.executeQuery();
                    vehicleSe = FXCollections.observableArrayList();
                    //System.out.println(view.getValue());
                    while (rs.next()) {

                        String regNo = rs.getString(1);
                        String name = rs.getString(2);
                        String type = rs.getString(3);
                        String cost = rs.getString(4);
                        String lifeTime = rs.getString(6);
                        String boughtDate = rs.getString(5);
                        String condition = rs.getString(7);
                        String depPercent = rs.getString(9);
                        String currentDep = rs.getString(10);
                        String totalDep = rs.getString(11);
                        String currentValue = rs.getString(12);
                        vehicleSe.add(new VehicleDetail(regNo, name, type, cost, lifeTime, boughtDate, condition, depPercent, currentDep, totalDep, currentValue));

                    }
                    vehicleTab.setItems(vehicleSe);

                } else if ((String) view.getValue() == "Available" || (String) view.getValue() == "Repair" || (String) view.getValue() == "Assigned") {
                    PreparedStatement pst = con.prepareStatement("select * from asset where name like '%" + search.getText() + "%' and availability = '" + (String) view.getValue() + "'"
                            + "union select * from asset where regNo like '%" + search.getText() + "%'and availability = '" + (String) view.getValue() + "'"
                            + "union select * from asset where type like '%" + search.getText() + "%'and availability = '" + (String) view.getValue() + "'"
                            + "union select * from asset where cost like '%" + search.getText() + "%'and availability = '" + (String) view.getValue() + "'"
                            + "union select * from asset where asset.condition like '%" + search.getText() + "%'and availability = '" + (String) view.getValue() + "'");

                    ResultSet rs = pst.executeQuery();
                    vehicleSe = FXCollections.observableArrayList();
                    System.out.println(view.getValue());
                    while (rs.next()) {

                        String regNo = rs.getString(1);
                        String name = rs.getString(2);
                        String type = rs.getString(3);
                        String cost = rs.getString(4);
                        String lifeTime = rs.getString(6);
                        String boughtDate = rs.getString(5);
                        String condition = rs.getString(7);
                        String depPercent = rs.getString(9);
                        String currentDep = rs.getString(10);
                        String totalDep = rs.getString(11);
                        String currentValue = rs.getString(12);
                        vehicleSe.add(new VehicleDetail(regNo, name, type, cost, lifeTime, boughtDate, condition, depPercent, currentDep, totalDep, currentValue));

                    }
                    vehicleTab.setItems(vehicleSe);

                }
                else if (search.getText().equals("")) {
                    updateLoad();
                }

            } catch (SQLException ex) {
                System.err.println("Error loading table data " + ex);

            } catch (IOException ex) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
    
    
@FXML
    private void showReport(){// throws JRException, SQLException, ClassNotFoundException {
     try{ 
         if(view.getValue()=="View_All")
         {
          DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\VINUSH\\Documents\\NetBeansProjects\\dkjconstruction\\DKJ ASSETS MANAGEMENT\\src\\dkj\\assets\\management\\All_Vehi.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
         }
         
         else if(view.getValue()=="Available"){
             DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\VINUSH\\Documents\\NetBeansProjects\\dkjconstruction\\DKJ ASSETS MANAGEMENT\\src\\dkj\\assets\\management\\Avai_Vehi.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
         }
         else if(view.getValue()=="Assigned"){
              DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\VINUSH\\Documents\\NetBeansProjects\\dkjconstruction\\DKJ ASSETS MANAGEMENT\\src\\dkj\\assets\\management\\Assigned.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
         }
         else if(view.getValue()=="Repair"){
             DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\VINUSH\\Documents\\NetBeansProjects\\dkjconstruction\\DKJ ASSETS MANAGEMENT\\src\\dkj\\assets\\management\\Repair_Vehi.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
         }
            
         
        
        
     }
     catch (Exception ex){System.out.println("error"+ ex);}
    }


}
