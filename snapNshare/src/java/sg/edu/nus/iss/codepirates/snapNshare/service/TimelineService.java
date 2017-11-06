/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import sg.edu.nus.iss.codepirates.snapNshare.ejb.FriendsEJB;
import sg.edu.nus.iss.codepirates.snapNshare.ejb.TimelineEJB;
import sg.edu.nus.iss.codepirates.snapNshare.entity.Photos;

/**
 * REST Web Service
 *
 * @author divah
 */
@Path("timeline")
public class TimelineService {

    @EJB
    private FriendsEJB friendsEJB;

    @EJB
    private TimelineEJB timelineEJB;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public TimelineService() {
    }

    /**
     * Retrieves representation of an instance of
     * sg.edu.nus.iss.codepirates.snapNshare.UserResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPhotos(@PathParam("username") String userName) {

        JsonObjectBuilder photosBuilder;
        JsonArrayBuilder photosArray
                = Json.createArrayBuilder();
        String url = "";

        List<String> friendList
                = friendsEJB.getFriends(userName);

        List<Photos> photos = timelineEJB.getPhotos(friendList);

        for (Photos photo : photos) {
            url = "";
            url = url.concat("http://172.17.251.113:8080/codepirates/snapNshare/timeline?imageName=").concat(photo.getId());
            photosBuilder = Json.createObjectBuilder();
            photosBuilder.add("postedBy", photo.getPostedBy());
            photosBuilder.add("url", url);
            photosBuilder.add("comment", photo.getComment());
            photosBuilder.add("postTime", photo.
                    getPostedTime().toString());
            photosArray.add(photosBuilder);
        }

        return photosArray.build().toString();

    }

    @GET
    @Produces("image/png")
    public Response getJson(@QueryParam("imageName") String imgName) {

        System.out.println(imgName);
        if (null != imgName) {
            byte[] img = timelineEJB.getImg(imgName);

            return Response.ok().entity(new StreamingOutput() {
                @Override
                public void write(OutputStream output)
                        throws IOException, WebApplicationException {
                    output.write(img);
                    output.flush();
                }
            }).build();
        } else {
            return null;
        }

    }

    /**
     * PUT method for updating or creating an instance of UserResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
