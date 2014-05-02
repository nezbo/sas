/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Controller.ControllerFactory;
import Database.DBConnection;
import Model.Relationship;
import Model.User;
import java.util.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;

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
        List<String> strings = new ArrayList<>();
        for (User u : ControllerFactory.getController().getAllUsers()) {
            strings.add("{ \"name\" : \""+u.getName()+"\", \"link\" : \"/ssasf14/service/users/"+u.getId()+"\"}");
        }
        
        return "{ \"users\" : ["+StringUtils.join(strings, ", ")+"] }";
    }
    
    @GET 
    @Path("users/{id}")
    public String getUsers(@PathParam("id") int id ){
        User u = ControllerFactory.getController().getUser(id);
        if(u==null) return "{}";
        StringBuilder b = new StringBuilder("{ \"name\" : \""+u.getName()+"\", \"address\" : \""+u.getAddress()+"\", \"hobbies\" : \""+u.getHobbies()+"\"");
        
        List<String> strings = new ArrayList<>();
        for (Relationship r : ControllerFactory.getController().getRelationships(u.getUsername())) {
            strings.add("{ \"relationshiptype\" : \""+r.getRelationshipType().getType()+"\", \"name\" : \""+r.getToUser().getName()+"\", \"link\" : \"/ssasf14/service/users/"+r.getToUser().getId()+"\"}");
        }
        b.append(", \"relationships\" : [ ");
        b.append(StringUtils.join(strings, ", "));
        b.append(" ] }");
        return b.toString();
    }
}
