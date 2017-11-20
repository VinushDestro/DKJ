/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.home;

/**
 *
 * @author User
 */
public class Detail {
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;

    public Detail(String col1, String col2, String col3, String col4, String col5) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
    }
    
    public Detail(String col1, String col2, String col3) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
      
    }
    
    public String getCol1() {
        return this.col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return this.col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return this.col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return this.col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return this.col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }
    
    
}
