/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Customer;

/**
 *
 * @author Manish
 * Bean for Customer
 */

@Named("customerBean")
@SessionScoped
public class CustomerBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private CartBean cartBean;

    private Customer customerDetails;    

    public Customer getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Customer customerDetails) {
        this.customerDetails = customerDetails;
    }
       
    /**
     * Creates a new instance of CustomerBean
     */
    public CustomerBean() {
    }
    
    @PostConstruct
    public void init(){
        customerDetails = new Customer();
    }

}
