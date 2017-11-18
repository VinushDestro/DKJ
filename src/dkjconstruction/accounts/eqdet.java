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
public class eqdet {
    private String eqdate;
    private String eqtype;
    private String eqamount;


public eqdet(String eqdate,String eqtype,String eqamount){
    this.eqdate = eqdate;
    this.eqtype = eqtype;
     this.eqamount = eqamount;
    } ;

    public String getEqdate() {
        return eqdate;
    }

    public void setEqdate(String eqdate) {
        this.eqdate = eqdate;
    }

    public String getEqtype() {
        return eqtype;
    }

    public void setEqtype(String eqtype) {
        this.eqtype = eqtype;
    }

    public String getEqamount() {
        return eqamount;
    }

    public void setEqamount(String eqamount) {
        this.eqamount = eqamount;
    }



}
