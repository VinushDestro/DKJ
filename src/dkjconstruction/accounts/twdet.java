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
public class twdet {
      private String twdate;
    private String twtype;
    private String twamount;
    private String twid;
    
     
    public twdet(String twdate,String twtype,String twamount,String twid){
     this.twdate = twdate;
    this.twtype = twtype;
     this.twamount = twamount;
     this.twid=twid;
    } ;
    

    public String getTwdate() {
        return twdate;
    }

    public void setTwdate(String twdate) {
        this.twdate = twdate;
    }

    public String getTwtype() {
        return twtype;
    }

    public void setTwtype(String twtype) {
        this.twtype = twtype;
    }

    public String getTwamount() {
        return twamount;
    }

    public void setTwamount(String twamount) {
        this.twamount = twamount;
    }

    public String getTwid() {
        return twid;
    }

    public void setTwid(String twid) {
        this.twid = twid;
    }
}
