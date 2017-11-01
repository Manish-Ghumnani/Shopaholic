package sg.edu.nus.iss.codepirates.shoppingcart.bean;
 
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
 
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.codepirates.shoppingcart.facade.UsersFacade;
 
@Named
@RequestScoped
public class UserLoginView {
     
    @EJB
    UsersFacade userFacade;
   
    private String username;
    
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    
    public String authenticate() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
         
        if(userFacade.isAuthenticated(username, password)) {
            loggedIn = true;
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
//            FacesContext.getCurrentInstance().addMessage(null, message);
             context.addCallbackParam("loggedIn", loggedIn);
            return ("shopping");
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials"); 
            FacesContext.getCurrentInstance().addMessage(null, message);
             context.addCallbackParam("loggedIn", loggedIn);
             return("login");
        }
          
             
    }   
}