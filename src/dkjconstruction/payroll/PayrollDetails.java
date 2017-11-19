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
    
    
    private String Empid;
    private String OverTime;
    private String HourlyRate;
    private String Salary;
    private String FinalSalary;
    private String Allowance;
    private String Deduction;
    
    

    public PayrollDetails(String Empid, String OverTime, String HourlyRate, String Salary, String FinalSalary) {
        this.Empid = Empid;
        this.OverTime = OverTime;
        this.Deduction = HourlyRate;
        this.Allowance = Salary;
        this.FinalSalary = FinalSalary;
    }
    
  
 
public PayrollDetails(String Empid, String OverTime,String HourlyRate,String Salary, String FinalSalary,String Allowance, String Deduction  ) {
        this.Empid = Empid;
        this.OverTime = OverTime;
        this.HourlyRate = HourlyRate;
        this.Salary = Salary;
        this.FinalSalary = FinalSalary;
        this.Allowance = Allowance;
        this.Deduction = Deduction;
        
      
    
     }

    public String getEmpid() {
        return Empid;
    }

    public void setEmpid(String Empid) {
        this.Empid = Empid;
    }

/*    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    } */

    public String getAllowance() {
        return this.Allowance;
    }

    public void setAllowance(String Allowance) {
        this.Allowance = Allowance;
    }

    public String getDeduction() {
        return this.Deduction;
    }

    public void setDeduction(String Deduction) {
        this.Deduction = Deduction;
    }

 /*   public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String Attendance) {
        this.Attendance = Attendance;
    } */

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String Salary) {
        this.Salary = Salary;
    }

    public String getOverTime() {
        return OverTime;
    }

    public void setOverTime(String OverTime) {
        this.OverTime = OverTime;
    }

    public String getHourlyRate() {
        return HourlyRate;
    }

    public void setHourlyRate(String HourlyRate) {
        this.HourlyRate = HourlyRate;
    }

    public String getFinalSalary() {
        return FinalSalary;
    }

    public void setFinalSalary(String FinalSalary) {
        this.FinalSalary = FinalSalary;
    }
}