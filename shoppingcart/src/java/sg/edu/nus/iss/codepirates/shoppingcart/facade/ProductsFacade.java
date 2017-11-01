/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Prasanna
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Product>{

    @PersistenceContext(unitName = "shoppingcartPU")
    EntityManager em;

    public ProductsFacade() {
        super(Product.class);
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
   
    
}
