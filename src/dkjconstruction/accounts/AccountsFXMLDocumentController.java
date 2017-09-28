/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.accounts;

import dkjconstruction.DbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author h3
 */
public class AccountsFXMLDocumentController implements Initializable {

    @FXML
    private Tab hracc;
    @FXML
    private JFXButton hrreport;
    @FXML
    private TableView<Hrdet> hrtab;
    @FXML
    private TableColumn<?, ?> hrdatecol;
    @FXML
    private TableColumn<?, ?> hrtypecol;
    @FXML
    private TableColumn<?, ?> hramountcol;
    @FXML
    private Tab hracc1;
    @FXML
    private JFXButton assreport;
    @FXML
    private TableView<Assdet> asstab;
    @FXML
    private TableColumn<?, ?> assdatecol;
    @FXML
    private TableColumn<?, ?> asstypecol;
    @FXML
    private TableColumn<?, ?> assamountcol;
    @FXML
    private Tab hracc11;
    @FXML
    private JFXButton matreport;
    @FXML
    private TableView<matdet> mattab;
    @FXML
    private TableColumn<?, ?> matdatecol;
    @FXML
    private TableColumn<?, ?> mattypecol;
    @FXML
    private TableColumn<?, ?> matamountcol;
    @FXML
    private Tab hracc111;
    @FXML
    private JFXButton autilreport;
    @FXML
    private TableView<utdet> utiltabb;
    @FXML
    private TableColumn<?, ?> autildatecol;
    @FXML
    private TableColumn<?, ?> autiltypecol;
    @FXML
    private TableColumn<?, ?> autilamount;
    @FXML
    private Tab hracc1111;
    @FXML
    private JFXButton twreport;
    @FXML
    private TableView<twdet> twtab;
    @FXML
    private TableColumn<?, ?> twdatecol;
    @FXML
    private TableColumn<?, ?> twtypecol;
    @FXML
    private TableColumn<?, ?> twamountcol;
    @FXML
    private TableColumn<?, ?> twtenidcol;

    @FXML
    private TableView<tendet> tentab;
    @FXML
    private TableColumn<?, ?> tencol;
    @FXML
    private JFXTextField twtxt;

    private ObservableList<Hrdet> hrdata;
    private ObservableList<matdet> matdata;
    private ObservableList<Assdet> assdata;
    private ObservableList<utdet> utdata;
    private ObservableList<twdet> twdata;
    private ObservableList<tendet> tendata;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        utdata = FXCollections.observableArrayList();
        hrdata = FXCollections.observableArrayList();
        matdata = FXCollections.observableArrayList();
        assdata = FXCollections.observableArrayList();
        twdata = FXCollections.observableArrayList();
        tendata = FXCollections.observableArrayList();

        setTableut();
        loadtableut();

        setTablehr();
        loadtablehr();

        setTableass();
        loadtableass();

        setTablemat();
        loadtablemat();

        setTabletw();
        loadtabletw();

        setTableten();
        loadtableten();

        searchtw();
    }

    //utility
    private void setTableut() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            autildatecol.setCellValueFactory(new PropertyValueFactory<>("uudate"));
            autiltypecol.setCellValueFactory(new PropertyValueFactory<>("uutype"));
            autilamount.setCellValueFactory(new PropertyValueFactory<>("uuamount"));
            System.out.println("Succuss load table");
        } catch (Exception e) {
            System.err.println("ut tbl set error" + e);

        }

    }

    private void loadtableut() {
        utdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select billType , paidAmount , paidDate from utilities");
            while (rs.next()) {

                utdata.add(new utdet("" + rs.getDate(3), "" + rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }
            System.out.println("Succuss load ut");

        } catch (Exception e) {
            System.err.println("err util table load" + e);
        }
        utiltabb.setItems(utdata);

    }

//hr
    private void setTablehr() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            hrdatecol.setCellValueFactory(new PropertyValueFactory<>("hdate"));
            hrtypecol.setCellValueFactory(new PropertyValueFactory<>("htype"));
            hramountcol.setCellValueFactory(new PropertyValueFactory<>("hamount"));
            ;
        } catch (Exception e) {
            System.err.println("hr tbl set error" + e);

        }

    }

    private void loadtablehr() {
        hrdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select p.empId,p.date,p.salary\n"
                    + "from payroll p ,emptender e\n"
                    + "where p.empId = e.empId"
            );
            while (rs.next()) {

                hrdata.add(new Hrdet("" + rs.getDate(2), rs.getString(1), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err hr table load" + e);
        }
        hrtab.setItems(hrdata);

    }

    //asset
    private void setTableass() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            assdatecol.setCellValueFactory(new PropertyValueFactory<>("adate"));
            asstypecol.setCellValueFactory(new PropertyValueFactory<>("atype"));
            assamountcol.setCellValueFactory(new PropertyValueFactory<>("aamount"));

        } catch (Exception e) {
            System.err.println("ass tbl set error" + e);

        }

    }

    private void loadtableass() {
        assdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select type , cost , boughtDate from asset");
            while (rs.next()) {

                assdata.add(new Assdet("" + rs.getDate(3), rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err ass table load" + e);
        }
        asstab.setItems(assdata);

    }

    //material
    private void setTablemat() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            matdatecol.setCellValueFactory(new PropertyValueFactory<>("mdate"));
            mattypecol.setCellValueFactory(new PropertyValueFactory<>("mtype"));
            matamountcol.setCellValueFactory(new PropertyValueFactory<>("mamount"));

        } catch (Exception e) {
            System.err.println("mat tbl set error" + e);

        }

    }

    private void loadtablemat() {
        matdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select m.date, m.itemType,(r.price*j.materialCount)\n"
                    + "from jobmaterial j, rawmaterial r , materialtender m\n"
                    + "where j.tenderId = m.tenderId and r.type = j.materialType");
            while (rs.next()) {

                matdata.add(new matdet("" + rs.getDate(1), rs.getString(2), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err mat table load" + e);
        }
        mattab.setItems(matdata);

    }

    //tw
    private void setTabletw() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            twdatecol.setCellValueFactory(new PropertyValueFactory<>("twdate"));
            twtypecol.setCellValueFactory(new PropertyValueFactory<>("twtype"));
            twamountcol.setCellValueFactory(new PropertyValueFactory<>("twamount"));
            twtenidcol.setCellValueFactory(new PropertyValueFactory<>("twid"));

        } catch (Exception e) {
            System.err.println("tw tbl set error" + e);

        }

    }

    private void loadtabletw() {
        twdata.clear();

        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select m.date, m.itemType,j.tenderId,(r.price*j.materialCount)\n"
                    + "from jobmaterial j, rawmaterial r , materialtender m\n"
                    + "where j.tenderId = m.tenderId and r.type = j.materialType UNION\n"
                    + "\n"
                    + "select p.date,p.empId,e.tenderId,p.salary\n"
                    + "  from payroll p ,emptender e\n"
                    + "  where p.empId = e.empId"
            );
            while (rs.next()) {

                //twdata.add(new twdet("" + rs.getDate(3), rs.getString(1), "" + rs.getDouble(2)));
                twdata.add(new twdet("" + rs.getDate(1), rs.getString(2), "" + rs.getString(4), "" + rs.getString(3)));

            }

        } catch (Exception e) {
            System.err.println("err tw table load" + e);
        }
        twtab.setItems(twdata);

    }

//ten
    private void setTableten() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            tencol.setCellValueFactory(new PropertyValueFactory<>("tenid"));

        } catch (Exception e) {
            System.err.println("ten tbl set error" + e);

        }

    }

    private void loadtableten() {
        tendata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select tenderId from tender");
            while (rs.next()) {

                tendata.add(new tendet(rs.getString(1)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err ten table load" + e);
        }
        tentab.setItems(tendata);

    }

    private void searchtw() {
        twtxt.setOnKeyReleased(e -> {

            if (twtxt.getText().equals("")) {
                setTabletw();
                loadtabletw();
            } else {
                twdata.clear();
                try {

                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery("select m.date, m.itemType,j.tenderId,(r.price*j.materialCount)\n"
                            + "from jobmaterial j, rawmaterial r , materialtender m\n"
                            + "where j.tenderId = m.tenderId and r.type = j.materialType and j.`tenderId`like '%"+twtxt.getText() +"%' union\n"
                            + "\n"
                            + "select p.date,p.empId,e.tenderId,p.salary\n"
                            + "  from payroll p ,emptender e\n"
                            + "  where p.empId = e.empId and e.`tenderId`like '%"+twtxt.getText() +"%'");

//"LIKE '%"+tid.getText() +"%'"
                    while (rs.next()) {

                        twdata.add(new twdet("" + rs.getDate(1), rs.getString(2), "" + rs.getString(4), "" + rs.getString(3)));

                    }

                } catch (Exception ex) {
                    System.err.println("Error searching" + ex);

                }

                twtab.setItems(twdata);
            }

        });

    }

    @FXML
    private void showReportPurchase(ActionEvent event) {
    }

} //L
