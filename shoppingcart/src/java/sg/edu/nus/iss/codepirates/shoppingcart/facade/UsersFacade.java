/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.shoppingcart.facade;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.codepirates.shoppingcart.model.Users;

/**
 *
 * @Prasanna
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "shoppingcartPU")
    private EntityManager em;
    
    private Users loggedInUser;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    public boolean isAuthenticated(final String username, final String password) {
        Users user = find(username);
        if(user != null && user.getPassword().equals(password)){
            loggedInUser = user;
            return true;
        }
        return false;
    }
    
    public void addUser(Users u){
        create(u);
    }
    

    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }
    
    public void logout() {
        loggedInUser = null;
    }
    
}
