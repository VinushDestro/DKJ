/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.*;
import static com.sun.javafx.tk.Toolkit.getToolkit;
import dkjconstruction.DbConnection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import dkjconstruction.tendermanagement.TenderReport.Report;

/**
 *
 * @author KishBelic
 */
public class TenderController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<TenderDetails> data;

    @FXML
    private AnchorPane centerpane;

    @FXML
    public JFXTextField tenderId, tenderName, companyName, period, bidValidity, tsearch;

    @FXML
    private JFXComboBox category, workType, place, grade;

    @FXML
    private TableView<TenderDetails> ttable;

    @FXML
    private TableColumn<?, ?> column_tid;
    @FXML
    private TableColumn<?, ?> column_tname;
    @FXML
    private TableColumn<?, ?> column_tgrade;
    @FXML
    private TableColumn<?, ?> column_tcategory;
    @FXML
    private TableColumn<?, ?> column_tworktype;
    @FXML
    private TableColumn<?, ?> column_tdate;
    @FXML
    private TableColumn<?, ?> column_tplace;
    @FXML
    private TableColumn<?, ?> column_tclient;

    @FXML
    private TableColumn<?, ?> column_tperiod;
    @FXML
    private TableColumn<?, ?> column_bidvalidity;
    @FXML
    private TableColumn<?, ?> column_tcost;

    @FXML
    private DatePicker tdate;

    @FXML
    private javafx.scene.control.Button previousButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //   category_opt.getItems().removeAll(category.getItems());
        category.getItems().addAll("Asphalt", "Gravel", "Concrete");
        //   category_opt.getSelectionModel().select("Option B");

        workType.getItems().addAll("Construct", "Re-fill", "Cleaning", "Painting");

        place.getItems().addAll("Batticaloa", "Trincomalee", "Kandy");

        grade.getItems().addAll("C6", "C7", "C8");

        data = FXCollections.observableArrayList();
        assignTableValues();
        loadDBtoTable();
        RowclickEvent();
        searchTender();

    }

    @FXML
    private void addClicked(ActionEvent event) throws SQLException {

        System.out.println(tenderId.getText().length());

        if (validatefields()) {

            try {

                DbConnection.openConnection();
                con = DbConnection.getConnection();
                pst = con.prepareStatement("insert into tender (tenderId,tenderName,grade,category,workType,workingPlace,companyName,period,bidValidity,date,status,estimatedCost,cost) "
                        + "values ('" + tenderId.getText() + "','" + tenderName.getText() + "','" + grade.getValue().toString() + "','" + category.getValue().toString() + "','" + workType.getValue().toString() + "','" + place.getValue().toString() + "','" + companyName.getText() + "'," + Integer.parseInt(period.getText()) + "," + Integer.parseInt(bidValidity.getText()) + ",'" + java.sql.Date.valueOf(tdate.getValue()) + "','Pending',0,0)");

                pst.executeUpdate();

                alerboxInfo("Add Tender", "Value added successfully");

                assignTableValues();
                loadDBtoTable();
                clearFields();

                //  DbConnection.closeConnection();
            } catch (SQLIntegrityConstraintViolationException r) {

                alertboxWarn("Add Tender", "This Tender detail(s) already taken");
                System.out.println(" Error" + r);
            } catch (Exception e) {

                System.out.println(" Error");
                System.err.println(e);

            }

        }//if-else

    }

    //assign Tenderdetails to table
    public void assignTableValues() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            column_tid.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            column_tname.setCellValueFactory(new PropertyValueFactory<>("TenderName"));
            column_tgrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
            column_tcategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
            column_tworktype.setCellValueFactory(new PropertyValueFactory<>("WorkType"));
            column_tplace.setCellValueFactory(new PropertyValueFactory<>("WorkingPlace"));
            column_tclient.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            column_tperiod.setCellValueFactory(new PropertyValueFactory<>("Period"));
            column_bidvalidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            column_tdate.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            column_tcost.setCellValueFactory(new PropertyValueFactory<>("EstimatedCost"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load tenderDB to table
    public void loadDBtoTable() {

        data.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM tender");
            rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        ttable.setItems(data);

    }

    //rowclick for tendertable
    private void RowclickEvent() {
       
        
            
            ttable.setOnMouseClicked((e) -> {
            TenderDetails t1 = ttable.getItems().get(ttable.getSelectionModel().getSelectedIndex());

            tenderId.setText(t1.getTenderId());
            tenderName.setText(t1.getTenderName());
            grade.setValue(t1.getGrade());
            category.setValue(t1.getCategory());
            workType.setValue(t1.getWorkType());
            place.setValue(t1.getWorkingPlace());
            companyName.setText(t1.getCompanyName());
            period.setText(t1.getPeriod());
            bidValidity.setText(t1.getBidValidity());
            tdate.setValue(LocalDate.parse(t1.getTdate()));

        });
            
        
        

    }

    @FXML
    private void updateClicked(ActionEvent event) {
        if (validatefields()) {

            if (alertboxConfirm("Update tender details !", "Do you really want to update")) {

                try {

                    pst = con.prepareStatement("UPDATE tender set tenderName= '" + tenderName.getText() + "',grade= '" + grade.getValue() + "',category = '" + category.getValue() + "',workType = '" + workType.getValue() + "',workingPlace = '" + place.getValue() + "',companyName = '" + companyName.getText() + "',period = '" + Integer.parseInt(period.getText()) + "',bidValidity = '" + Integer.parseInt(bidValidity.getText()) + "',date = '" + tdate.getValue() + "'  where tenderId = '" + tenderId.getText() + "' ");
                    pst.execute();

                    alerboxInfo("Add Tender", "Details updated successfully");

                } catch (Exception e) {
                    System.out.println("Update  error");

                }

                assignTableValues();
                loadDBtoTable();

            }

        }
    }

    @FXML
    private void deleteClicked(ActionEvent event) {

        
        
        if (TenderManagement.checkClosedCancelOngoing(tenderId.getText(),"Closed")) {
            alertboxWarn("Alert", "Can not delete Closed Tender..!!");
            return;
        }
        
        if (TenderManagement.checkClosedCancelOngoing(tenderId.getText(),"Cancelled")) {
            alertboxWarn("Alert", "Can not delete Cancelled Tender..!!");
            return;
        }
        
        if (TenderManagement.checkClosedCancelOngoing(tenderId.getText(),"On progress")) {
            alertboxWarn("Alert", "Can not delete Ongoing Tender..!!");
            return;
        }

        if (TenderManagement.checkRequired(tenderId.getText()) && alertboxConfirm("Delete Tender", "Requirements will be deleted from this Tender... Do you really want to Delete ?")) {
            deleterequired();
            deleteClicked();
            return;
        }

        if (!TenderManagement.checkRequired(tenderId.getText()) && alertboxConfirm("Delete tender details !", "Do you really want to Delete ?")) {

            deleteClicked();

        }

    }

    private void deleteClicked() {

        try {

            pst = con.prepareStatement("DELETE from tender where tenderId = '" + tenderId.getText() + "' ");
            pst.execute();

            alerboxInfo("Delete Tender", "Tender deleted successfully");

        } catch (Exception e) {
            System.out.println("del  error" + e);

        }

        assignTableValues();
        loadDBtoTable();

    }

    private void deleterequired() {

        try {
            pst = con.prepareStatement("DELETE from jobasset  where tenderId = '" + tenderId.getText() + "' ");
            pst.execute();

        } catch (Exception e) {
            System.out.println("del  error" + e);
        }

        try {
            pst = con.prepareStatement("DELETE from equiptender  where tenderId = '" + tenderId.getText() + "' ");
            pst.execute();

        } catch (Exception e) {
            System.out.println("del  error" + e);
        }

        try {
            pst = con.prepareStatement("DELETE from jobemployee  where tenderId = '" + tenderId.getText() + "' ");
            pst.execute();

        } catch (Exception e) {
            System.out.println("del  error" + e);
        }

        try {
            pst = con.prepareStatement("DELETE from materialtender  where tenderId = '" + tenderId.getText() + "' ");
            pst.execute();

        } catch (Exception e) {
            System.out.println("del  error" + e);
        }

        assignTableValues();
        loadDBtoTable();

    }

    private void viewClicked(ActionEvent event) throws IOException {

        //   FXMLLoader fxmlLoader = FXMLLoader(getClass().getResource("Demo.fxml"));
        //    Parent root1 = (Parent) fxmlLoader.load();
        Parent root1 = FXMLLoader.load(getClass().getResource("ViewTender.fxml"));
        Scene root1_scene = new Scene(root1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(root1_scene);

        stage.show();

        /*
        try {
            AnchorPane cpane = FXMLLoader.load(getClass().getResource("ViewTender.fxml"));
            centerpane.getChildren().setAll(cpane);
        
        } catch (IOException ex) {
            Logger.getLogger(TenderController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
         */
    }

    //search
    private void searchTender() {
        tsearch.setOnKeyReleased(e -> {
            if (tsearch.getText().equals("")) {
                loadDBtoTable();
            } else {
                data.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where tenderName LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where grade LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where category LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where estimatedCost LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where workingPlace LIKE '%" + tsearch.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                ttable.setItems(data);

            }

        });//event
    }

    //alertbox Information
    public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox warning
    public void alertboxWarn(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox Confirmation
    public boolean alertboxConfirm(String title, String message) {

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

    //validation
    private boolean validatefields() {

        if (tenderId.getText().isEmpty() || tenderName.getText().isEmpty() || (grade.getValue() == null)
                || (category.getValue() == null) || (workType.getValue() == null) || (place.getValue() == null)
                || companyName.getText().isEmpty() || period.getText().isEmpty() || bidValidity.getText().isEmpty()
                || (tdate.getValue() == null)) {
            alertboxWarn("Add Tender", "All fields should be filled !");
            //  cssLabel.getStylesheets().add("error");

            return false;

        } else if (tenderId.getText().length() > 5) {
            alertboxWarn("Add Tender", "Tender id should be less than 5 digits");
            return false;
        } else if (validateTextfield.validateNumber(period, "Period Input invalid", "Input should be in numbers") && validateTextfield.validateNumber(bidValidity, "bidValidity Input invalid", "bidValidity should be in numbers")) {

            return false;
        } else if (Integer.parseInt(period.getText()) <= Integer.parseInt(bidValidity.getText())) {
            alertboxWarn("Add Tender", "Bid Validity should be less than Tender Period");
            period.setText("");
            bidValidity.setText("");

            return false;

        } else if (tdate.getValue().isAfter(LocalDate.now())) {

            alertboxWarn("Add Tender", "date input is invalid");
            return false;
        } else {

            return true;

        }

    }

    public void clearFields() {
        tenderId.setText("");
        bidValidity.setText("");
        category.setValue("");
        companyName.setText("");
        grade.setValue("");
        period.setText("");
        place.setValue("");
        tenderName.setText("");
        tdate.setValue(null);
        workType.setValue("");

    }

    //report

    public void tender_report() {        

        Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\tendermanagement\\TenderReport\\AllTender.jrxml");

    }

    @FXML
    private void keytyped(javafx.scene.input.KeyEvent event) {

    }

    @FXML
    private void previousClicked(ActionEvent event) throws IOException {
//        Stage s = (Stage) previousButton.getScene().getWindow();
//        s.close();

        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TenderHome.fxml"));
         Parent root1 = (Parent) fxmlLoader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root1));
         stage.show();*/
        
        dkjconstruction.DKJConstruction.showTender_TenderHome();
        
        
    }

    @FXML
    private void reqClicked(ActionEvent event) throws IOException {

//        Stage s = (Stage) previousButton.getScene().getWindow();
//        s.close();

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Requirement.fxml"));
//        Parent root1 = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root1));
//        stage.show();
dkjconstruction.DKJConstruction.showTender_requirement();

    }

}
