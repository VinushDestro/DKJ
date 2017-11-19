/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import dkjconstruction.DbConnection;
import dkjconstruction.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author KishBelic
 */
public class findCost {
    
        public static void main (String[] args)
                
{
    
   double value = findcost("T0001","material");
   double v2 = findcost("T0001","transport");
   double v3 = findcost("T0001","total");
   double v4 = findcost("T0001","hrsum");
   
    System.out.println(value +" "+ v2 +" "+ v3 +" "+ v4);


}
    
    public static double  findcost(String tenderId,String costType)
    {
        double hrsum = 0;
                double matsum = 0;
                double transsum = 0;
                double totsum = 0;
                
            try {
                

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();

                ResultSet rs3 = con.createStatement().executeQuery("select SUM(r.price*m.assignCount)\n"
                        + "from rawmaterial r , materialtender m\n"
                        + "where r.type = m.materialType and tenderId like '%" + tenderId + "%'\n"
                        + "");

                while (rs3.next()) {

                    double c = rs3.getDouble(1);
                    matsum = matsum + c;
                }

                ResultSet rs2 = con.createStatement().executeQuery("select sum(cost)\n"
                        + "from transport t \n"
                        + "where t.`tenderId` = '" + tenderId + "'\n"
                        + "  ");

                while (rs2.next()) {

                    double d = rs2.getDouble(1);
                    transsum = transsum + d;
                }

                ResultSet rs9 = con.createStatement().executeQuery("select sum(p.finalSalary)\n"
                        + " from payroll p ,emptender e\n"
                        + " where p.empId = e.empId and e.`tenderId` = '" + tenderId + "'");

                while (rs9.next()) {

                    double d = rs9.getDouble(1);
                    hrsum = hrsum + d;
                }

                totsum = transsum + matsum + hrsum;

                                                    
                
                
                

            } catch (Exception e) {
                System.out.println("error calculating" + e);
            }
            
            
            if(costType.equals("material"))
                {
                System.out.println(Double.toString(matsum));
                return matsum;
                
                }
                
                else if(costType.equals("transport"))
                {
                System.out.println(Double.toString(transsum));
                return transsum;
                
                }
                else if(costType.equals("total"))
                {
                System.out.println(Double.toString(totsum));
                return totsum;               
                }
                else if(costType.equals("hrsum"))
                {
                System.out.println(Double.toString(hrsum));
                return hrsum;               
                }
                
                else{
                    return -1;
                }
            
        }
    
}



