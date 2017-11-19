/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;
//import dkj.construction.DbConnection; 
/**
 *
 * @author Ranjitha
 */
 public class JobEmployee {
    private String tenderId;
    private Integer empCount;
    private Integer assigncount;

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    public Integer getAssigncount() {
        return assigncount;
    }

    public void setAssigncount(Integer assigncount) {
        this.assigncount = assigncount;
    }

    public JobEmployee(String tenderId, Integer empCount, Integer assigncount) {
        this.tenderId = tenderId;
        this.empCount = empCount;
        this.assigncount = assigncount;
    }

   

    

    /**
     * @return the tenderId
     */
   
   
    
 }