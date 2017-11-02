package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sg.edu.nus.iss.codepirates.shoppingcart.ejb.ProductEJB;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Product;

/**
 *
 * @author Divahar Sethuraman Bean for Product
 */
@Named("productBean")
@RequestScoped
public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProductEJB prodEjb;

    private StreamedContent productImage;
    private List<Product> productList;

    public void setProductImage(StreamedContent productImage) {
        this.productImage = productImage;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() throws IOException, SQLException {
        return prodEjb.getProductDetails();
    }

    public StreamedContent getProductImage() throws IOException, SQLException {

        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {

            String id = context.getExternalContext().getRequestParameterMap()
                    .get("pid");

            byte[] image = prodEjb.getProdImage(id);

            return new DefaultStreamedContent(new ByteArrayInputStream(image));

        }
    }

    public String home() {
        return "welcome";
    }

    public String shopping() {
        return "shopping";
    }

    public String checkout() {
        return "checkout";
    }

}
