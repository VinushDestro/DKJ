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
public class VINU {
    private String tenderId;
    private String type;
    private Integer req;
    private Integer assign;

    public VINU(Integer req, Integer assign) {
        this.req = req;
        this.assign = assign;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public VINU(String tenderId, String type, Integer req, Integer assign) {
        this.tenderId = tenderId;
        this.type = type;
        this.req = req;
        this.assign = assign;
    }

   
   
}
