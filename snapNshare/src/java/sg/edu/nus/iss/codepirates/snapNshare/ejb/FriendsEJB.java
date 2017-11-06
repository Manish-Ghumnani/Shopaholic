/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.codepirates.snapNshare.entity.Friends;
import sg.edu.nus.iss.codepirates.snapNshare.entity.FriendsId;

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
    
    public List<String> getFriends(String userName)
    {
        String queryString = "select f from Friends f where (f.friendsId.friendRef = :user)";
        TypedQuery<Friends> query = em.createQuery(queryString, Friends.class);
        query.setParameter("user", userName);
        
        List<Friends> friends = query.getResultList();
        List<String> friendNames = new ArrayList<String>();
        friends.forEach((f) -> {
            friendNames.add(f.getFriendsId().getFriendId());
        });
        return friendNames;
    }
    

}
