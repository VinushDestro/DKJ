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
public class Assdet {
      private String adate;
    private String atype;
    private String aamount;
    
    
     
    public Assdet(String adate,String atype,String aamount){
     this.adate = adate;
    this.atype = atype;
     this.aamount = aamount;
    } ;

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getAamount() {
        return aamount;
    }

    public void setAamount(String aamount) {
        this.aamount = aamount;
    }
    
}
