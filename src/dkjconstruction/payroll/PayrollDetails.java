/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.payroll;

/**
 *
 * @author ASRAJ
 */
public class PayrollDetails {
    
    
    private String EmpId;
    private String Date;
    private String Bonus;
    private String Allowance;
    private String Attendance;
    private String Salary;
   // private String ChooseEmployeeType;
   

    public PayrollDetails(String EmpId, String Date, String Bonus, String Allowance, String Attendance, String Salary) {
        this.EmpId = EmpId;
        this.Date = Date;
        this.Bonus = Bonus;
        this.Allowance = Allowance;
        this.Attendance = Attendance;
        this.Salary = Salary;
    // this.ChooseEmployeeType = ChooseEmployeeType;
       
    }

   
   

   

   /* public String getChooseEmployeeType() {
        return ChooseEmployeeType;
    }

    public void setChooseEmployeeType(String ChooseEmployeeType) {
        this.ChooseEmployeeType = ChooseEmployeeType;
    }
*/
    /**
     * @return the EmpId
     */
    public String getEmpId() {
        return EmpId;
    }

    /**
     * @param EmpId the EmpId to set
     */
    public void setEmpId(String EmpId) {
        this.EmpId = EmpId;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Date;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Date = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getBonus() {
        return Bonus;
    }

    public void setBonus(String Bonus) {
        this.Bonus = Bonus;
    }

    public String getAllowance() {
        return Allowance;
    }

    public void setAllowance(String Allowance) {
        this.Allowance = Allowance;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String Attendance) {
        this.Attendance = Attendance;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String Salary) {
        this.Salary = Salary;
    }

}