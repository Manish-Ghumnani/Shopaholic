/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author divah
 */

@Entity
@Table(name = "friends")
public class Friends implements Serializable{
    
    private static final long serialVersionUID = 1L;
  
    @EmbeddedId
    private FriendsId friendsId;
     
    @Column(name = "created_at")
    private Date createdAt;

    public FriendsId getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(FriendsId friendsId) {
        this.friendsId = friendsId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
    
   
}
