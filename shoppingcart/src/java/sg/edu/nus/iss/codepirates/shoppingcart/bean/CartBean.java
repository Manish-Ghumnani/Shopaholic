/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman
 * Managed Bean for Cart
 */

@Named
@SessionScoped
public class CartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int quantity;
    private List<Product> products;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addToCart(Product product) {
        System.out.println("Prod Id:" + product.getProductId());
        System.out.println("Quantity:" + quantity);
        boolean isAdded = false;
        if (null != products
                && products.size() > 0) {
            for (Product prod : products) {
                if (prod.getProductId().equals(product.getProductId())) {
                    int qty = prod.getQuantity();
                    int newQty = qty + quantity;
                    prod.setQuantity(newQty);
                    isAdded = true;
                }
            }
            if (!isAdded) {         
                product.setQuantity(quantity);
                products.add(product);
            }
        } else {
            products = new ArrayList<>();
            product.setQuantity(quantity);
            products.add(product);
        }
    }

    public void cartQuantity(ValueChangeEvent event) {
        quantity = (Integer) event.getNewValue();
    }

    public String checkout() {
        return ("checkout");
    }
}
