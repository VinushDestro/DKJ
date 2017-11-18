/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.vehicle;

import dkjconstruction.DbConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author h3
 */
public class vehicleaccounts {
    
            public static int addEE(String regNo,double cost,Date boughtDat) throws SQLException, ClassNotFoundException {
          
                       
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
       
        PreparedStatement stmt = con.prepareStatement("insert into vehicleaccounts (`regNo`,`cost`,`boughtDate`) values (?,?,?)");
        
        stmt.setString(1,regNo);
        stmt.setDouble(2,cost);
        stmt.setDate(3,boughtDat);
      
                
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();
  
        return result;
                
        }
    
}
