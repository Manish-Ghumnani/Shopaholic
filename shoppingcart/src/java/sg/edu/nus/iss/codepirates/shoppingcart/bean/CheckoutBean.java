/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.EmailEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.ProductEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;


/**
 *
 * @author Divahar Sethuraman Managed Bean for Checkout
 */

@Named("checkoutBean")
@SessionScoped
public class CheckoutBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Inject
    private CartBean cartBean;
    
    @Inject
    private CustomerBean custBean;
    
    @EJB private ProductEJB prodEjb;
    
    @EJB private EmailEJB emailEJB;    
    
     public String back(){
         return "shopping";
    }
    
    public String buy() throws IOException{          
        if(null!=cartBean.getProducts() &&
                cartBean.getProducts().size()>0){
        for(Product prod:cartBean.getProducts()){            
            prod.setAvailable(prod.getAvailable()-
                            prod.getQuantity());
        }
      }
        prodEjb.update(cartBean.getProducts());  
        emailEJB.send(custBean.getCustomerDetails().
                getEmail(), cartBean.getProducts(), String.valueOf(cartBean.getCartTotal()));
                
        return "thankyou";              
    }       
    
    public void order(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    String servername = externalContext.getRequestServerName();
    String port = String.valueOf(externalContext.getRequestServerPort());
    String appname = externalContext.getRequestContextPath();
    String protocol = externalContext.getRequestScheme();
    HttpSession session = (HttpSession) externalContext.getSession(true);
    String url = protocol + "://" + servername + ":" + port + appname + "/print.xhtml;jsessionid="+session.getId()+"?pdf=true";    
        
    try {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(new URL(url).toString());
        renderer.layout();
       
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"order.pdf");
        OutputStream browserStream = response.getOutputStream();
        renderer.createPDF(browserStream);       

    } catch (Exception ex) {
        ex.printStackTrace();
    }
        facesContext.responseComplete();
    }
    
     public String home() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "welcome";              
    }  
 
}
