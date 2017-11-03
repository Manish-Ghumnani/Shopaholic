/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import com.lowagie.text.DocumentException;
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
import sg.edu.nus.iss.codepirates.shoppingcart.common.ShoppingCartConstants;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.EmailEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.ProductEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.QueueEJB;

/**
 *
 * @author Divahar Sethuraman Bean for Checkout
 */

@Named("checkoutBean")
@SessionScoped
public class CheckoutBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CartBean cartBean;

    @Inject
    private CustomerBean custBean;

    @EJB
    private ProductEJB prodEjb;

    @EJB
    private QueueEJB queueEJB;

    @EJB
    private EmailEJB emailEJB;

    public String back() {
        return "shopping";
    }

    public String buy() throws IOException {
        if (null != cartBean.getProducts()
                && cartBean.getProducts().size() > 0) {
            cartBean.getProducts().stream().forEach((prod) -> {
                prod.setAvailable(prod.getAvailable()
                        - prod.getQuantity());
            });
        }
        prodEjb.update(cartBean.getProducts());
        queueEJB.sendJMSMessageToWarehouseQueue(cartBean.getProducts(),
                custBean.getCustomerDetails().getName(), custBean.getCustomerDetails()
                .getAddress(), custBean.getCustomerDetails().getComment());
        emailEJB.send(custBean.getCustomerDetails().
                getEmail(), cartBean.getProducts(), String.valueOf(cartBean.getCartTotal()));

        return "thankyou.xhtml?faces-redirect=true";

    }

    public void order() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String servername = externalContext.getRequestServerName();
        String port = String.valueOf(externalContext.getRequestServerPort());
        String appname = externalContext.getRequestContextPath();
        String protocol = externalContext.getRequestScheme();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        String url = protocol + "://" + servername + ":" + port + appname + "/print.xhtml;jsessionid=" + session.getId() + "?pdf=true";

        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(new URL(url).toString());
            renderer.layout();

            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.reset();
            response.setContentType(ShoppingCartConstants.APPLN_PDF);
            response.setHeader(ShoppingCartConstants.CONTENT_DISP, ShoppingCartConstants.ATTACH);
            OutputStream browserStream = response.getOutputStream();
            renderer.createPDF(browserStream);

        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
        facesContext.responseComplete();
    }

    public String home() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "welcome.xhtml?faces-redirect=true";
    }

}
