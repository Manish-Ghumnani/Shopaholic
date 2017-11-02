/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Customer;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

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
