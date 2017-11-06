/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.codepirates.snapNshare.entity.User;

/**
 *
 * @author Prasanna
 */
@Stateless
public class RegisterEJB {

  @PersistenceContext
  EntityManager em;
  
  
  public void createUser(User user){
  if(user!=null)
  em.persist(user);
  else
          System.err.println("User Object is Null cannot be Persisted");
  
  }
    
}
