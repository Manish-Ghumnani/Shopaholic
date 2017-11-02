/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.codepirates.shoppingcart.common.ShoppingCartConstants;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman Bean for Cart
 */

@Named("cartBean")
@SessionScoped
public class CartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int quantity;
    private List<Product> products;
    private BigDecimal cartTotal;

    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

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
        boolean isAdded = false;
        String result = "";
        String msg = "";
        boolean isError = false;

        if (null != products
                && products.size() > 0) {
            for (Product prod : products) {
                if (prod.getProductId().equals(product.getProductId())) {
                    int qty = prod.getQuantity();
                    int newQty = qty + quantity;

                    if (newQty <= prod.getAvailable()) {
                        prod.setQuantity(newQty);
                        float total = prod.getPrice()
                                * newQty;
                        prod.setTotalPrice(total);
                        isAdded = true;
                        result = ShoppingCartConstants.SUCCESS;
                        msg = product.getProductName() + ShoppingCartConstants.CART_SUCCESS;
                    } else {
                        isError = true;
                        result = ShoppingCartConstants.UNSUCCESS;
                        msg = ShoppingCartConstants.CART_UNSUCCESS;
                    }
                }
            }
            if (!isAdded && !isError) {
                if (quantity <= product.getAvailable()) {
                    updateCart(product);
                    products.add(product);
                    result = ShoppingCartConstants.SUCCESS;
                    msg = product.getProductName() + ShoppingCartConstants.CART_SUCCESS;
                } else {
                    result = ShoppingCartConstants.UNSUCCESS;
                    msg = ShoppingCartConstants.CART_UNSUCCESS;
                }
            }
        } else {
            products = new ArrayList<>();
            if (quantity <= product.getAvailable()) {
                updateCart(product);
                products.add(product);
                result = ShoppingCartConstants.SUCCESS;
                msg = product.getProductName() + ShoppingCartConstants.CART_SUCCESS;
            } else {
                result = ShoppingCartConstants.UNSUCCESS;
                msg = ShoppingCartConstants.CART_UNSUCCESS;
            }
        }

        quantity = 0;

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(result, msg));

    }

    public void cartQuantity(ValueChangeEvent event) {
        quantity = (Integer) event.getNewValue();
    }

    public String checkout() throws IOException {

        boolean isError = false;
        String error = "";
        cartTotal = BigDecimal.ZERO;
        if (null != products
                && products.size() > 0) {
            for (Product prod : products) {
                if (null != prod && !isError) {
                    if (prod.getQuantity() > 0) {
                        cartTotal = cartTotal.add(new BigDecimal(prod.getTotalPrice())).setScale(2, RoundingMode.HALF_EVEN);
                    } else {
                        isError = true;
                        error = ShoppingCartConstants.CHKOUT_QTY_ERR;
                    }
                }
            }
        } else {
            isError = true;
            error = ShoppingCartConstants.CHKOUT_NOITEM_ERR;
        }

        if (isError) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ShoppingCartConstants.UNSUCCESS,
                    error));
            return ("shopping");
        } else {
            return ("checkout");
        }
    }

    public void cart() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("cartview", options, null);
    }

    public void removeFromCart(Product product) {
        if (null != products
                && products.size() > 0) {
            for (Product prod : products) {
                if (prod.getProductId().equals(product.getProductId())) {
                    products.remove(prod);
                    break;
                }
            }
        }
    }

    public void updateQuantity(Product product) {
        String result = "";
        String msg = "";
        if (null != products
                && products.size() > 0) {
            for (Product prod : products) {
                if (prod.getProductId().equals(product.getProductId())) {
                    if (quantity <= prod.getAvailable()) {
                        updateCart(prod);
                        result = ShoppingCartConstants.SUCCESS;
                        msg = product.getProductName() + " updated.";
                    } else {
                        result = ShoppingCartConstants.UNSUCCESS;
                        msg = ShoppingCartConstants.CART_UNSUCCESS;
                    }
                    break;
                }
            }

            quantity = 0;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(result, msg));
        }
    }

    public void updateCart(Product product) {
        product.setQuantity(quantity);
        float total = product.getPrice()
                * quantity;
        product.setTotalPrice(total);
    }
}
