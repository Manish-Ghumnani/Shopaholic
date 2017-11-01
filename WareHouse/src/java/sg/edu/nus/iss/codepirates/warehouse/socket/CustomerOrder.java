package sg.edu.nus.iss.codepirates.warehouse.socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *Message Driven Bean to read Data from JMS Queue and Send it to angular Application
 * @author Prasanna
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/warehouseQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class CustomerOrder implements MessageListener {
    
    private static final Logger log = Logger.getLogger(CustomerOrder.class.getSimpleName());
 
    @Inject
    IncomingOrder iorder;
   
    
    @Override
    public void onMessage(Message message) {
          TextMessage result = (TextMessage)message;
         //List<Cart> cartList = new ArrayList<>();
          
        try {
            System.out.println("....Received .......--->>>>" + result.getText());
           
              
             iorder.onMessage(result.getText());
          
            
           /* JsonReader reader = Json.createReader(new StringReader(result.getText()));
             JsonObject product= (JsonObject) reader.readObject();
             Order order = new Order();
             order.setName(product.getString("name"));
             order.setAddress(product.getString("address"));
             order.setComment(product.getString("comment"));
             
           for(JsonValue val:product.getJsonArray("cart")){
                            JsonObject item = (JsonObject) val;
                            Cart cart = new Cart();
                            cart.setItem(item.getString("item"));
                            cart.setQuantity(item.getInt("quantity"));
                            cartList.add(cart);             
           }
             
            order.setCart(cartList);
            */
            
        } catch (JMSException ex) {
            Logger.getLogger(CustomerOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    
    }
    
  
    
}
