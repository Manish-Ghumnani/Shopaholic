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
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.ProductEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;


/**
 *
 * @author Divahar Sethuraman Managed Bean for Checkout
 */

@Named("checkoutBean")
@SessionScoped
public class CheckoutBean implements Serializable {

    @Inject
    private CartBean cartBean;
    
    @EJB private ProductEJB prodEjb;
    
    private String name;
    private String address;
    private int zipCode;
    private int mobile;
    private String email;
   
    public CartBean getCartBean() {
        return cartBean;
    }

    public void setCartBean(CartBean cartBean) {
        this.cartBean = cartBean;
    }

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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String back(){
         return "shopping";
    }
    
    public String buy() throws IOException{
        String error = "";
        if(null!=cartBean.getProducts() &&
                cartBean.getProducts().size()>0){
        for(Product prod:cartBean.getProducts()){            
            prod.setAvailable(prod.getAvailable()-
                            prod.getQuantity());
        }
      }
        prodEjb.update(cartBean.getProducts());        
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
    System.out.println("url: "+url);
        
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
