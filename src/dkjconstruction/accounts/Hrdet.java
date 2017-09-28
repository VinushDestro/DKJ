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
public class Hrdet {

    private String hdate;
    private String htype;
    private String hamount;
    
    
    public Hrdet(String hdate,String htype,String hamount){
     this.hdate = hdate;
    this.htype = htype;
     this.hamount = hamount;
    } ;

    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    public String getHtype() {
        return htype;
    }

    public void setHtype(String htype) {
        this.htype = htype;
    }

    public String getHamount() {
        return hamount;
    }

    public void setHamount(String hamount) {
        this.hamount = hamount;
    }

}
