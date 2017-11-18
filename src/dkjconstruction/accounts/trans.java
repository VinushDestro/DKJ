/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.accounts;

/**
 *
 * @author h3
 */
public class trans {
    
    
    private String tdate;
    private String ttype;
    private String tamount;
    
      public trans(String tdate, String ttype, String tamount) {
        this.tdate = tdate;
        this.ttype = ttype;
        this.tamount = tamount;
    };

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getTamount() {
        return tamount;
    }

    public void setTamount(String tamount) {
        this.tamount = tamount;
    }
    
    
}
