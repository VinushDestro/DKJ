/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import dkjconstruction.DbConnection;
import dkjconstruction.accounts.dwdet;
import java.sql.ResultSet;
import java.sql.SQLException;
import dkjconstruction.equip.EquipController;

/**
 *
 * @author h3
 */
public class acc {

    public static void mn() throws ClassNotFoundException, SQLException {

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("\n"
                + "        INSERT INTO eqaccounts (name,count,cost) SELECT t1.name,t1.count,t1.cost\n"
                + "  FROM equipment t1\n"
                + " WHERE t1.name NOT IN (SELECT name\n"
                + "                       FROM eqaccounts)");

        stmt.executeUpdate();

    }
    
        //accounts
        public static int addE(String name,int count,double cost) throws SQLException, ClassNotFoundException {
          
                       
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
       
        PreparedStatement stmt = con.prepareStatement("insert into eqaccounts (`name`,`count`,`cost`) values (?,?,?)");
        
        stmt.setString(1,name);
        stmt.setInt(2,count);
        stmt.setDouble(3,cost);
      
                
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();
  
        return result;
                
        }
        //////////

 

}
