/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Divahar Sethuraman This class contains the persistence logic for
 * Product
 *
 */
@Stateless
public class FriendsEJB {

    @PersistenceContext
    private EntityManager em;
    
    

}
