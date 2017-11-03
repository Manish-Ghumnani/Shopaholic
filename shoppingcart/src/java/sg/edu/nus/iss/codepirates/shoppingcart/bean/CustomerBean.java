/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import sg.edu.nus.iss.codepirates.shoppingcart.facade.UsersFacade;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Customer;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Users;

/**
 *
 * @author Manish
 * Bean for Customer
 */

@Named("customerBean")
@SessionScoped
public class CustomerBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @JMSConnectionFactory("jms/connectionFactory")
    @Inject
    private CartBean cartBean;

    @EJB
    UsersFacade userFacade;
    
    private Customer customerDetails;    
    
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
   
    
    
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
        user = new Users();
    }
    
    public void addCustomer(){
    userFacade.addUser(user);
    }
    
    public String  login(){
    return "login";
    }
    

}
