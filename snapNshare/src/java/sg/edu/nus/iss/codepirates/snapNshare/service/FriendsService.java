/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.service;


import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.codepirates.snapNshare.ejb.FriendsEJB;

/**
 * REST Web Service
 *
 * @author divah
 */
@Path("friends")
public class FriendsService {

    @EJB
    private FriendsEJB friendEjb;

    @Context
    private UriInfo context;
    
    @EJB
    FriendsEJB friendsEJB;

    /**
     * Creates a new instance of UserResource
     */
    public FriendsService() {
    }

    /**
     * Retrieves representation of an instance of
     * sg.edu.nus.iss.codepirates.snapNshare.UserResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("friendRef") String friendRef) {

        return "";
    }

    @POST
    @Path("/{friendRef}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addFriends(List<String> friendList,
            @PathParam("friendRef") String friendRef) {
        System.out.println("In add friends");
        JsonArrayBuilder builder
                = Json.createArrayBuilder();

        if (null != friendRef
                && null != friendList && friendList.size() > 0) {
            friendEjb.addFriends(friendRef, friendList);
            builder.add("Success");
        } else {
            builder.add("Failure");
        }

        return builder.build().toString();
     }
    
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFriends(@PathParam("username") String userName) {
        List<String> friendNames = friendsEJB.getFriends(userName);
        //System.out.println("here");
        JsonArrayBuilder nameBuilder = Json.createArrayBuilder();
        for(String name:friendNames){
            nameBuilder.add(name);  
            System.out.println(name);
        }
    
        return nameBuilder.build().toString();
        //return (Response.ok(nameBuilder, MediaType.APPLICATION_JSON).build());
        
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
