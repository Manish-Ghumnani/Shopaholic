/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
import sg.edu.nus.iss.codepirates.shoppingcart.bean.CustomerBean;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman Queue EJB for handling the queues
 */
@Stateless
public class QueueEJB {

    @Resource(mappedName = "jms/warehouseQueue")
    private Queue warehouseQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    public void sendJMSMessageToWarehouseQueue(List<Product> productList,
            String name, String address, String comment) {
        try {
            JsonArrayBuilder cartBuilder = Json.createArrayBuilder();
            JsonObjectBuilder prodBuilder;

            if (!productList.isEmpty()) {

                for (Product product : productList) {
                    prodBuilder = Json.createObjectBuilder();
                    prodBuilder.add("item", product.getProductName())
                            .add("quantity", product.getQuantity());
                    cartBuilder.add(prodBuilder);

                }

            }

            JsonObject customer = Json.createObjectBuilder().add("name", name)
                    .add("address", address).add("comment", comment)
                    .add("cart", cartBuilder)
                    .build();

            TextMessage textMessage = context.createTextMessage();
            textMessage.setText(customer.toString());
            context.createProducer().send(warehouseQueue, textMessage);

        } catch (JMSException ex) {
            Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
