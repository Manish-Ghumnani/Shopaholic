package sg.edu.nus.iss.codepirates.shoppingcart.bean;

import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.codepirates.shoppingcart.facade.UsersFacade;
import sg.edu.nus.iss.codepirates.shoppingcart.util.Utility;

@Named("userLoginBean")
@RequestScoped
public class UserLoginBean {
     
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
             HttpSession session= Utility.getSession();
             session.setAttribute("username", username);
            context.addCallbackParam("loggedIn", loggedIn);
            return ("shopping");
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials"); 
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            return("welcome");
        }
             
    }   
    
    
    public String logout(){
  
        HttpSession session =   Utility.getSession();
        session.invalidate();
        return("welcome");
    }
}
