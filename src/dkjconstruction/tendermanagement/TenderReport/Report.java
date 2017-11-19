/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tendermanagement.TenderReport;

import dkjconstruction.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author KishBelic
 */
public class Report {
    
    
    public static void gen_preport(String qry,int no,String xyz)
    {
        try
        {
        JasperReport jasperReport=JasperCompileManager.compileReport(xyz);
        Connection con = DbConnection.getConnection();
        PreparedStatement st = con.prepareStatement(qry);
        st.setInt(1,no);
        ResultSet rset = st.executeQuery();
        JRResultSetDataSource jasperReports = new JRResultSetDataSource(rset);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, jasperReports);

        String filename=null;
        filename="APPEND.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,filename);
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " +filename);
       }
       catch(Exception e)
       {
           System.out.println(e);
       }  
    }
    
    
    public static void gen_Normal_report(String report) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        
        } catch (Exception e) {
            System.out.println("error in j report" + e);
        }


    }
    
}
