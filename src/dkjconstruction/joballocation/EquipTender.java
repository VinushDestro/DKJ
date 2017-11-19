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
//Class EQUI Referencing equipTender
public class EquipTender {
    
   
    private String tenderId;
    private String equiName;

    public EquipTender(String equiName) {
        this.equiName = equiName;
    }
    private Integer req;
    private Integer assign;

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getEquiName() {
        return equiName;
    }

    public void setEquiName(String equiName) {
        this.equiName = equiName;
    }

    public Integer getReq() {
        return req;
    }

    public void setReq(Integer req) {
        this.req = req;
    }

    public Integer getAssign() {
        return assign;
    }

    public void setAssign(Integer assign) {
        this.assign = assign;
    }

    public EquipTender(String tenderId, String equiName, Integer req, Integer assign) {
        this.tenderId = tenderId;
        this.equiName = equiName;
        this.req = req;
        this.assign = assign;
    }

    public EquipTender(Integer req) {
        this.req = req;
    }
   
   
    
}
