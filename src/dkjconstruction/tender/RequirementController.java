/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.*;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.management.Notification;

/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class RequirementController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<TenderDetails> data;
    private ObservableList tidList;

    private ObservableList<material> matData;
    private ObservableList rawtypeList;

    private ObservableList<asset> assetData;
    private ObservableList assettypeList;

    private ObservableList<equip> equipData;
    private ObservableList equiptypeList;
    
    private ObservableList<TenderDetails> empData;

    @FXML
    private javafx.scene.control.Button previousButton;
    @FXML
    private AnchorPane centerpane;

    @FXML
    private JFXComboBox r_tid;

    //material componens  
    @FXML
    private JFXComboBox r_rawType;
    @FXML
    private JFXTextField r_units;
    @FXML
    private Label r_uPrice;
    @FXML
    private TableView<material> mtable;
    @FXML
    private TableColumn<?, ?> mcolumn_Name;
    @FXML
    private TableColumn<?, ?> mcolumn_units;


    //asset componens
    @FXML
    private JFXComboBox r_assetName;
    @FXML
    private Label r_auPrice;
    @FXML
    private JFXTextField r_aunits;
    @FXML
    private TableView<asset> atable;
    @FXML
    private TableColumn<?, ?> acolumn_assetName;
    @FXML
    private TableColumn<?, ?> acolumn_units;

    //equip componens
    @FXML
    private JFXComboBox r_equipName;
    private Label r_euPrice;
    @FXML
    private JFXTextField r_eunits;
    @FXML
    private TableView<equip> etable;
    @FXML
    private TableColumn<?, ?> ecolumn_name;
    @FXML
    private TableColumn<?, ?> ecolumn_units;

    
    @FXML
    private Label lbl_estimatedCost;
    
    //employee
    @FXML
    private JFXTextField r_empUnits;
    @FXML
    private TableView<TenderDetails> emptable;
    @FXML
    private TableColumn<?, ?> empcolumn_units;
    @FXML
    private Button btn_tender_report;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        btn_tender_report.setVisible(false);
        data = FXCollections.observableArrayList();

        tidList = FXCollections.observableArrayList();
        tenderCombo();

        rawtypeList = FXCollections.observableArrayList();
        rawCombo();
        matData = FXCollections.observableArrayList();
        assign_mTableValues();

        assettypeList = FXCollections.observableArrayList();
        assetCombo();
        assetData = FXCollections.observableArrayList();
        assign_assetTableValues();

        equiptypeList = FXCollections.observableArrayList();
        equipCombo();
        equipData = FXCollections.observableArrayList();
        assign_EquipTableValues();

        empData = FXCollections.observableArrayList();
        
        
    }

    //tender combobox drop down list
    private void tenderCombo() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT tenderId FROM tender ORDER BY tenderId ASC");
            rs = pst.executeQuery();

            while (rs.next()) {
                tidList.add(rs.getString(1));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading tender  " + e);
        }

        r_tid.setItems(tidList);
        
        

    }

    @FXML
    private void tenderComboClicked(ActionEvent event) {

        loadMaterialtoTable();
        loadAssettoTable();
        loadEquiptoTable();
        estimate();
        
        loadEmployee();
    }

    ///raw material combo box drop down
    private void rawCombo() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM rawmaterial");
            rs = pst.executeQuery();

            while (rs.next()) {
                rawtypeList.add(rs.getString(1));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading tender  " + e);
        }

        r_rawType.setItems(rawtypeList);

    }

    @FXML
    private void rawComboClicked() {
        
        if(validateR())
        {
        return;
            
        }
        
        r_units.setText(Integer.toString(1));
        setupriceLabel();
        


    }

    //set material price lbl
    private void setupriceLabel() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT price FROM rawmaterial where type = '" + r_rawType.getValue().toString() + "'");
            rs = pst.executeQuery();

            if (rs.next()) {

                double raw_price = rs.getDouble(1);
                double uPrice = 1.0 * Integer.parseInt(r_units.getText());

                r_uPrice.setText(Double.toString((raw_price * uPrice)));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading material price  " + e);
        }

    }

    @FXML
    private void rawPriceKeyReleased() {

        setupriceLabel();

    }

    //assign material details to table
    private void assign_mTableValues() {

        try {

            //Set cell value factory to tableview.
            mcolumn_Name.setCellValueFactory(new PropertyValueFactory<>("MaterialType"));
            mcolumn_units.setCellValueFactory(new PropertyValueFactory<>("MaterialCount"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load material DB to table
    private void loadMaterialtoTable() {

        matData.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM materialtender where tenderId = '" + r_tid.getValue().toString() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                matData.add(new material(rs.getString(2), rs.getInt(3)));
            }

        } catch (Exception e) {
            System.err.println("Error loading materialtender table data " + e);

        }
        mtable.setItems(matData);

    }
    
    //load employee table
    private void loadEmployee()
    {
        empData.clear();
        try {
            empcolumn_units.setCellValueFactory(new PropertyValueFactory<>("NoOfEmployee"));
            
        } catch (Exception e) {
            System.out.println("error in load table" + e);
        }
        
        try {
            

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM jobemployee where tenderId = '" + r_tid.getValue().toString() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                empData.add(new TenderDetails(""+rs.getInt(2)));
            }

        } catch (Exception e) {
            System.err.println("Error loading employee table data " + e);

        }
    emptable.setItems(empData);
    
    
    }

    //insert matrials values to DB
    private void insertMaterialDB() {

        try {

            //DbConnection.openConnection();
            con = DbConnection.getConnection();
            pst = con.prepareStatement("Insert into materialtender (tenderId,materialType,materialCount) values ('" + r_tid.getValue().toString() + "','" + r_rawType.getValue().toString() + "','" + Integer.parseInt(r_units.getText()) + "') ");
            pst.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e1) {

            if(TenderManagement.alertboxConfirm("Update details", "This details already added. Do you really want to update ?"))
            {   
                update("materialtender", "materialCount", Integer.parseInt(r_units.getText()), r_tid.getValue().toString(), "materialType", r_rawType.getValue().toString());                   
            }
            
            System.out.println(" duplication Error " + e1);

        }
        catch(NullPointerException e1){
            
        TenderManagement.alertboxWarn("Invalid Selection", "Select any value from Material list");
        }
        catch (Exception e) {
            System.out.println(" Error " + e);

        }

    }

    @FXML
    private void addmat() {
        
        if(validateR() || validateTextfield.validateNumberTextfield(r_units))
        {
        return;
            
        }
        
        try {

            insertMaterialDB();

        }catch (Exception e) {
            System.err.println("Error set table data " + e);

        }
        loadMaterialtoTable();
        estimate();

    }

    

    //asset material combo box drop down
    private void assetCombo() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT distinct type FROM asset");
            rs = pst.executeQuery();

            while (rs.next()) {
                assettypeList.add(rs.getString(1));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading asset drop down  " + e);
        }

        r_assetName.setItems(assettypeList);

    }

    @FXML
    private void assetComboClicked(ActionEvent event) {

        if(validateR())
        {
        return;
            
        }
        
        r_aunits.setText(Integer.toString(1));

        setAssetPriceLabel();
    }

    //set material price lbl
    private void setAssetPriceLabel() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT cost FROM asset where name = '" + r_assetName.getValue().toString() + "' ");
            rs = pst.executeQuery();

            if (rs.next()) {

                double asset_price = rs.getDouble(1);
                double uPrice = 1.0 * Integer.parseInt(r_aunits.getText());

               // r_auPrice.setText(Double.toString((asset_price * uPrice)));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading material price  " + e);
        }

    }

    
//insert employee values to DB
    @FXML
    private void addemp(ActionEvent event) {

        if(validateR() || validateTextfield.validateNumberTextfield(r_empUnits))
        {
        return;
            
        }
        
        try {

            //DbConnection.openConnection();
            con = DbConnection.getConnection();
            pst = con.prepareStatement("Insert into jobemployee (tenderId,noOfEmployee) values ('" + r_tid.getValue().toString() + "','" + Integer.parseInt(r_empUnits.getText().toString()) + "') ");
            pst.executeUpdate();

            //TenderManagement.notification();
        } catch (SQLIntegrityConstraintViolationException e1) {
            
            if(TenderManagement.alertboxConfirm("Update details", "This details already added. Do you really want to update ?"))
            {
                update("jobemployee", "noOfEmployee",Integer.parseInt(r_empUnits.getText()) , r_tid.getValue().toString());
            
                loadEmployee();
            }
            
            System.out.println(" duplication Error " + e1);
            
            

        }
        
        catch (Exception e) {
            System.out.println(" Error " + e);

        }
        
        loadEmployee();

    }

    @FXML
    private void assetPriceKeyReleased(KeyEvent event) {

        setAssetPriceLabel();
    }

    //assign asset details to table
    private void assign_assetTableValues() {

        try {

            //Set cell value factory to tableview.
            acolumn_assetName.setCellValueFactory(new PropertyValueFactory<>("AssetType"));
            acolumn_units.setCellValueFactory(new PropertyValueFactory<>("AssetCount"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load asset DB to table
    private void loadAssettoTable() {

        assetData.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM jobasset where tenderId = '" + r_tid.getValue().toString() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                assetData.add(new asset(rs.getString(2), rs.getInt(3)));
            }

        } catch (Exception e) {
            System.err.println("Error loading materialtender table data " + e);

        }
        atable.setItems(assetData);

    }

    //insert asset values to DB
    private void insertAssetDB() {

        try {

            DbConnection.openConnection();
            con = DbConnection.getConnection();
            pst = con.prepareStatement("Insert into jobasset (tenderId,assetType,assetCount) values ('" + r_tid.getValue().toString() + "','" + r_assetName.getValue().toString() + "','" + Integer.parseInt(r_aunits.getText()) + "') ");
            pst.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e1) {

            if(TenderManagement.alertboxConfirm("Update details", "This details already added. Do you really want to update ?"))
            {   
                update("jobasset", "assetCount", Integer.parseInt(r_aunits.getText()), r_tid.getValue().toString(), "assetType", r_assetName.getValue().toString());
            
                
            }
            
            System.out.println(" duplication Error " + e1);

        }
        catch(NullPointerException e1){
            
        TenderManagement.alertboxWarn("Invalid Selection", "Select any value from Asset list");
        }
        catch (Exception e) {
            System.out.println(" Error " + e);

        }

    }

    private void updateAssetDB() {

        if (TenderManagement.alertboxConfirm("Delete tender details !", "Do you really want to Delete ?")) {

            try {

                // pst = con.prepareStatement("DELETE from tender where tenderId = '" + tenderId.getText() + "' ");
                pst.execute();

            } catch (Exception e) {
                System.out.println("del  error");

            }

        }

    }

    @FXML
    private void addasset(ActionEvent event) {
        if(validateR() || validateTextfield.validateNumberTextfield(r_aunits))
        {
        return;
            
        }
        
        insertAssetDB();

        loadAssettoTable();
        estimate();

    }

    //equip combo box drop down
    private void equipCombo() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT name FROM equipment");
            rs = pst.executeQuery();

            while (rs.next()) {
                equiptypeList.add(rs.getString(1));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading equip drop down  " + e);
        }

        r_equipName.setItems(equiptypeList);

    }

    @FXML
    private void equipComboClicked(ActionEvent event) {

        if(validateR())
        {
        return;
            
        }
        
        r_eunits.setText(Integer.toString(1));

        setEquipPriceLabel();
    }

    //set eqip price lbl
    private void setEquipPriceLabel() {

        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT cost FROM equipment where name = '" + r_equipName.getValue().toString() + "' ");
            rs = pst.executeQuery();

            if (rs.next()) {

                double equip_price = rs.getDouble(1);
                double uPrice = 1.0 * Integer.parseInt(r_eunits.getText());

                r_euPrice.setText(Double.toString((equip_price * uPrice)));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading equip price  " + e);
        }

    }

    @FXML
    private void equipPriceKeyReleased(KeyEvent event) {
        setEquipPriceLabel();

    }

    //assign equip details to table
    private void assign_EquipTableValues() {

        try {

            //Set cell value factory to tableview.
            ecolumn_name.setCellValueFactory(new PropertyValueFactory<>("EquipName"));
            ecolumn_units.setCellValueFactory(new PropertyValueFactory<>("Count"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load equip DB to table
    private void loadEquiptoTable() {

        equipData.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select equipName,count from equiptender  where tenderId = '" + r_tid.getValue().toString() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                equipData.add(new equip(rs.getString(1), rs.getInt(2)));

            }

        } catch (Exception e) {
            System.err.println("Error loading equiptender table data " + e);

        }
        etable.setItems(equipData);

    }

    //insert equip values to DB
    private void insertEquipDB() {

        try {

            //DbConnection.openConnection();
            con = DbConnection.getConnection();
            pst = con.prepareStatement("Insert into equiptender (tenderId,equipName,count) values ('" + r_tid.getValue().toString() + "','" + r_equipName.getValue().toString() + "','" + Integer.parseInt(r_eunits.getText().toString()) + "') ");
            pst.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e1) {

            if(TenderManagement.alertboxConfirm("Update details", "This details already added. Do you really want to update ?"))
            {   
                update("equiptender", "count", Integer.parseInt(r_eunits.getText()), r_tid.getValue().toString(), "equipName", r_equipName.getValue().toString());                   
            }
            
            System.out.println(" duplication Error " + e1);

        } 
        catch(NullPointerException e1){
            
        TenderManagement.alertboxWarn("Invalid Selection", "Select any value from Equipment list");
        }
        catch (Exception e) {
            System.out.println(" Error " + e);

        }

    }

    @FXML
    private void addequip(ActionEvent event) {
        
     
        if(validateR()||validateTextfield.validateNumberTextfield(r_eunits) )
        {
        return;     
        }
        
        
        insertEquipDB();
        loadEquiptoTable();
        estimate();
            
        
        

    }

    private void estimate()
    {
    double esti = TenderManagement.findCost(r_tid.getValue().toString(), 'e', 'f');
    lbl_estimatedCost.setText(Double.toString(esti));
    
    TenderManagement.insertEsti_Cost(r_tid.getValue().toString(), esti);
    
    }
    
    
    private void update(String tableName,String ColumnName,int collumnvalue,String tid)
    {
    try {

                    pst = con.prepareStatement("UPDATE "+ tableName +" set "+ColumnName +" = "+collumnvalue +"  where tenderId = '" + tid + "' ");
                    pst.execute();

                } catch (Exception e) {
                    System.out.println("Update  error");

                }
       
    }
    
    private void update(String tableName,String unitname,int unitvalue,String tid,String type,String typename)
    {
    try {

                    pst = con.prepareStatement("UPDATE "+ tableName +" set "+unitname +" = '"+unitvalue +"'  where tenderId = '" + tid + "'  && "+type +" = '"+typename +"' ");
                    pst.execute();

                } catch (Exception e) {
                    System.out.println("Update  error");

                }
       
    }
    

    @FXML
    private void tender_report(ActionEvent event) {
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
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tender.fxml"));
//        Parent root1 = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root1));
//        stage.show();

dkjconstruction.DKJConstruction.showTender();
    }
    
    private boolean validateR()
    {
    boolean bool=false;
        if(r_tid.getValue()== null)
        {
        TenderManagement.alertboxWarn("Tender Id not provided", "Select tender to perform this action");
            
        bool = true;
        }
    
        return bool;
    }
    
    
}
