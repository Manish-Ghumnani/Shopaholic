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
import javax.inject.Named;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
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
 */
@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable{

    @Resource(mappedName = "jms/warehouseQueue")
    private Queue warehouseQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @Inject
    private CartBean cartBean;

   // private static final long serialVersionUID = 1L;
    private Customer customerDetails;
    //private String name;

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

    public void sendJMSMessageToWarehouseQueue() {
        try {
            //context.createProducer().send(warehouseQueue);
            List<Product> productList = cartBean.getProducts();
            JsonArrayBuilder cartBuilder = Json.createArrayBuilder();
            JsonObjectBuilder prodBuilder;
            
            if(!productList.isEmpty()){
                
                for(Product product :productList){
                    prodBuilder  = Json.createObjectBuilder();
                    prodBuilder.add("item", product.getProductName())
                            .add("quantity", product.getQuantity());
                    cartBuilder.add(prodBuilder);
                    
                }
                
            }
            
            
            JsonObject customer = Json.createObjectBuilder().add("name", customerDetails.getName())
                    .add("address",customerDetails.getAddress()).add("comment",customerDetails.getComment())
                    .add("cart", cartBuilder)
                    .build();
            
            TextMessage textMessage = context.createTextMessage();
            textMessage.setText(customer.toString());
            context.createProducer().send(warehouseQueue,textMessage);
            
            System.out.println(customer);
        } catch (JMSException ex) {
            Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
