/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.model;

import java.io.Serializable;
import javax.inject.Inject;
import sg.edu.nus.iss.codepirates.shoppingcart.bean.CartBean;

/**
 *
 * @author Manish
 */
public class Customer implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
 
    
    private String name;
    
    private String address;
    
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
