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
public class matTender {
     private String matTender;
     private String matType;
     private Integer reqCount;
     private Integer assignCount;

    public matTender(Integer assignCount) {
        this.assignCount = assignCount;
    }
     
     

    public String getMatTender() {
        return matTender;
    }

    public void setMatTender(String matTender) {
        this.matTender = matTender;
    }

    public String getMatType() {
        return matType;
    }

    public void setMatType(String matType) {
        this.matType = matType;
    }

    public Integer getReqCount() {
        return reqCount;
    }

    public void setReqCount(Integer reqCount) {
        this.reqCount = reqCount;
    }

    public Integer getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(Integer assignCount) {
        this.assignCount = assignCount;
    }

    public matTender(String matTender, String matType, Integer reqCount, Integer assignCount) {
        this.matTender = matTender;
        this.matType = matType;
        this.reqCount = reqCount;
        this.assignCount = assignCount;
    }
}
