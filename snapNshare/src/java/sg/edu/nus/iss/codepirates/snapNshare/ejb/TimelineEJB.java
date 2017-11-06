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
import javax.persistence.Query;
import sg.edu.nus.iss.codepirates.snapNshare.entity.Photos;

/**
 *
 * @author divah
 */
@Stateless
public class TimelineEJB {

    @PersistenceContext
    EntityManager em;

    public List<Photos> getPhotos(List<String> friends) {

        String posted = "";

        if (null != friends && friends.size() > 0) {
            for (String friend : friends) {
                posted = posted.concat("'").concat(friend).
                        concat("'").concat(",");
            }

            posted = posted.substring(0, posted.length() - 1);

            Query query = em.createQuery("SELECT p from Photos p where"
                    + " p.postedBy in (" + posted + ")");

            query.setMaxResults(5);

            System.out.println("query: " + query);

            return (List<Photos>) query.getResultList();
        } else {
            return null;
        }
    }

    public byte[] getImg(String imgId) {
        Photos photo = em.find(Photos.class, imgId);
        return photo.getImage();
    }

    public void UploadImage(Photos photo) {
        if (photo != null) {
            em.persist(photo);
        } else {
            System.err.println("Photo Object is Null cannot be Persisted");
        }

    }
}
