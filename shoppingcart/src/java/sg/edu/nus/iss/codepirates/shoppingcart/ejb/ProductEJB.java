/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sg.edu.nus.iss.codepirates.shoppingcart.facade.ProductsFacade;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman
 * This class contains the persistence logic for Product
 * 
 */

@Stateless
public class ProductEJB {

    @EJB
    ProductsFacade productFacade;
    
    
    public List<Product> getProductDetails() {
             return productFacade.findAll();
    }

    public byte[] getProdImage(String pid) {
      
         Product prod=   productFacade.find(pid);
        return prod.getProductImage();
    }
    
    public void update(List<Product> products){
        for(Product prod:products){            
            em.createQuery("update Product set available="+prod.getAvailable()+""
                    + " where productId='"+prod.getProductId()+"'").executeUpdate();
        }
    }
}
