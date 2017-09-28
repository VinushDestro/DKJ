/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

/**
 *
 * @author Ranjitha
 */
public class ASRAJ {
    
    private String empId;
    private String empName;
    private String type;
    
    
     public ASRAJ(String EmpId, String EmpName, String Type) {
        this.empId = EmpId;
        this.empName = EmpName;
        this.type = Type;
    }

    public void setEmpId(String EmpId) {
        this.empId = EmpId;
    }

    public void setEmpName(String EmpName) {
        this.empName = EmpName;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getType() {
        return type;
    }

   
    
    
    
    

}