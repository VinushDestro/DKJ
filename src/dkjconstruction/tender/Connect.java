package dkjconstruction.tender;


import java.sql.*;

public class Connect {
     
    public static Connection connect() {
    
        Connection con = null;
        
        
        try{
         
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dkj","root","");
           System.out.println("Connection Success"); 
        }
        
        catch(ClassNotFoundException | SQLException e){
        
                System.out.println("Connection Fail");
        }
        
  
        return con;
    }
}
