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
public class matbuy {

    private String mbdate;
    private String mbtype;
    private String mbamount;

    public matbuy(String mbdate, String mbtype, String mbamount) {
        this.mbdate = mbdate;
        this.mbtype = mbtype;
        this.mbamount = mbamount;
    };

    public String getMbdate() {
        return mbdate;
    }

    public void setMbdate(String mbdate) {
        this.mbdate = mbdate;
    }


    public String getMbtype() {
        return mbtype;
    }

    public void setMbtype(String mbtype) {
        this.mbtype = mbtype;
    }


    public String getMbamount() {
        return mbamount;
    }

    public void setMbamount(String mbamount) {
        this.mbamount = mbamount;
    }


}
