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
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman EJB for sending Email of order summary to customer
 */
@Stateless
public class EmailEJB {

    @Resource(name = "jndi/mail")
    private Session mailSession;

    public void send(String to, List<Product> products, String cartTotal) {

        try {
            StringBuilder email = new StringBuilder();
            Message msg = new MimeMessage(mailSession);
            msg.setSubject("Your order has been processed !");
            msg.setRecipient(RecipientType.TO, new InternetAddress(
                    to));
            msg.setFrom(new InternetAddress("codepirates.shop@gmail.com"));

            email.append("<p>Dear Customer,</p></br> <p>Please find your order summary</p></br></br>");

            email.append("<html><body>"
                    + "<table style='border:1px solid black' cellpadding=10>");

            email.append("<tr style='border:1px solid black'><th style='border:1px solid black'>Product ID</th><th style='border:1px solid black'>Product Name</th><th style='border:1px solid black'>Quantity</th><th style='border:1px solid black'>Total Price</th>");

            products.stream().forEach((prod) -> {
                append(email, prod.getProductId(), prod.getProductName(),
                        String.valueOf(prod.getQuantity()), "$ " + String.valueOf(prod.getTotalPrice()));
            });

            email.append("</table>");
            email.append("<br></br>");
            email.append("<p>Order Total: $ ").append(cartTotal).append("</p>");
            email.append("<br></br> <p>Thank you for shopping wih us..!!!</p>");
            email.append("</body></html>");
            msg.setContent(email.toString(), "text/html; charset=utf-8");

            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void append(StringBuilder email, String id, String name,
            String quantity, String price) {
        email.append("<tr style='border:1px solid black'>");
        email.append("<td style='border:1px solid black'>");
        email.append(id);
        email.append("</td>");
        email.append("<td style='border:1px solid black'>");
        email.append(name);
        email.append("</td>");
        email.append("<td style='border:1px solid black'>");
        email.append(quantity);
        email.append("</td>");
        email.append("<td style='border:1px solid black'>");
        email.append(price);
        email.append("</td>");
        email.append("</tr>");
    }
}
