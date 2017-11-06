/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author divah
 */

@Entity
@Table(name = "photos")
public class Photos implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    private String id;
   
    @Column(name = "image")
    @Lob
    private byte[] image;
    
    @Column(name = "comment")    
    private String comment;
    
    @Column(name = "postedBy")    
    private String postedBy;
    
    @Column(name = "postedTime")    
    private Timestamp postedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Timestamp postedTime) {
        this.postedTime = postedTime;
    }
  
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

}
