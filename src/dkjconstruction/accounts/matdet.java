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
public class matdet {
      private String mdate;
    private String mtype;
    private String mamount;
    
    
    public matdet(String mdate,String mtype,String mamount){
     this.mdate = mdate;
    this.mtype = mtype;
     this.mamount = mamount;
    } ;

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMamount() {
        return mamount;
    }

    public void setMamount(String mamount) {
        this.mamount = mamount;
    }
    
}
