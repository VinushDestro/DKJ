/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.rawmaterial;

import dkjconstruction.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author h3
 */
public class accraw {
    
        public static void mnq() throws ClassNotFoundException, SQLException {

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("\n"
                + "        INSERT INTO rawaccounts (type,quantity,price) SELECT t1.type,t1.quantity,t1.price\n"
                + "  FROM rawmaterial t1\n"
                + " WHERE t1.type NOT IN (SELECT type\n"
                + "                       FROM rawaccounts)");

        stmt.executeUpdate();

    }
            public static int addEOQ(String type,double price,int  quantity) throws SQLException, ClassNotFoundException {
          
                       
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
       
        PreparedStatement stmt = con.prepareStatement("insert into rawaccounts (`type`,`quantity`,`price`) values (?,?,?)");
        
        stmt.setString(1,type);
        stmt.setInt(2,quantity);
        stmt.setDouble(3,price);
      
                
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();
  
        return result;
                
        }
    
}
