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
public class utdet {
      private String uudate;
    private String uutype;
    private String uuamount;
    
    public utdet(String uudate,String uutype,String uuamount){
     this.uudate = uudate;
    this.uutype = uutype;
     this.uuamount = uuamount;
    } ;

    public String getUudate() {
        return uudate;
    }

    public void setUudate(String uudate) {
        this.uudate = uudate;
    }

    public String getUutype() {
        return uutype;
    }

    public void setUutype(String uutype) {
        this.uutype = uutype;
    }

    public String getUuamount() {
        return uuamount;
    }

    public void setUuamount(String uuamount) {
        this.uuamount = uuamount;
    }
    
}
