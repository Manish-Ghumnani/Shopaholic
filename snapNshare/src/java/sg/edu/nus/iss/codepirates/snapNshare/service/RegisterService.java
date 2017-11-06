/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.service;

import java.sql.Date;
import javax.ejb.EJB;
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
import sg.edu.nus.iss.codepirates.snapNshare.ejb.RegisterEJB;
import sg.edu.nus.iss.codepirates.snapNshare.entity.User;

/**
 * REST Web Service
 *
 * @author divah
 */
@Path("register")
public class RegisterService {

    @Context
    private UriInfo context;
    
    private User newUser;
    
    @EJB
    RegisterEJB register;

    /**
     * Creates a new instance of UserResource
     */
    public RegisterService() {
        newUser = new User();
    }

    /**
     * Retrieves representation of an instance of sg.edu.nus.iss.codepirates.snapNshare.UserResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "test";
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     * @param username
     */
    @POST
    @Path("/{username}")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void putJson(@PathParam("username") String username) {
        
        newUser.setUserId(username);
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        
        register.createUser(newUser);
    }
}
