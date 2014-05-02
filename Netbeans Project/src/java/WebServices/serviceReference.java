/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Rasztemberg
 */
@Path("")
public class serviceReference {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of serviceReference
     */
    public serviceReference() {
    }
    
    @GET
    @Path("users")
    @Produces("application/json")
    public String getUsers() { 
        return "users : []";
    }
    
    @GET 
    @Path("users/{name}")
    public String getUsers(@PathParam("name") String name ){
        
        return name;
    }
}
