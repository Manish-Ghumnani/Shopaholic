/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author divah
 */

@Embeddable
public class FriendsId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "friend_id")    
    private String friendId;

    @Column(name = "friend_ref")
    private String friendRef;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendRef() {
        return friendRef;
    }

    public void setFriendRef(String friendRef) {
        this.friendRef = friendRef;
    }
    
    
    
    
}
