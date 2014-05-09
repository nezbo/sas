/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.DBConnection;
import static Database.DBConnection.getUser;
import Model.RelationshipType;
import Model.Relationship;
import Model.User;
import java.util.List;

/**
 * Makes security checks before forwarding data to the same method in the facade class
 * @author dst
 */
public class SecurityController implements Controller {
    
    private static SecurityController controller = null;

    private SecurityController() {
        
    }
    
    protected static Controller getInstance()
    {
        if(controller==null)
            controller = new SecurityController();
        return controller;        
    }

    @Override
    public boolean authenticate(String username, String password) {
        // invalid input
         if(username != null && username.length() <= 31 
                && password != null && password.length() <= 255 ) {
              return FacadeController.getInstance().authenticate(username, password);
        } else {
             return false;
        }
       
    }
   
    @Override
    public boolean authenticateAdmin(String username, String password) {
        // invalid input
        if(username != null && username.length() <= 31 && password != null && password.length() <= 255) {
            return FacadeController.getInstance().authenticateAdmin(username, password);
        } else {
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        // invalid input
        if(username == null) return null;
        
        return FacadeController.getInstance().getUser(username);
    }

    @Override
    public User getUser(int id) {
        // invalid input
        if(id < 0) return null;
        
        return FacadeController.getInstance().getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return FacadeController.getInstance().getAllUsers();
    }

    @Override
    public boolean createUser(String username, String password) {
        // invalid values
        if(username == null || password == null || username.length() > 31 || password.length() > 252) //dont want too large names into database
            return false;
        
        // existing user
        if(DBConnection.getUser(username) != null) return false;
        
        return FacadeController.getInstance().createUser(username, password);
    }

    @Override
    public boolean updatePassword(String username, String password) {
        // Check length of values
        if(username == null || password == null || username.length() > 31 || password.length() > 255)
            return false;

        return FacadeController.getInstance().updatePassword(username, password);
    }

    @Override
    public boolean updateUserInfo(String username, String name, String address, String hobbies, String friends) {
        if(name==null || address==null || friends==null || hobbies==null)
            return false;
        
        if(name.length() > 255 
                || address.length() > 255 
                || friends.length() > 255)
            return false;
        
        return FacadeController.getInstance().updateUserInfo(username, name, address, hobbies, friends);
    }

    @Override
    public List<RelationshipType> getRelationShipTypes() {
            return FacadeController.getInstance().getRelationShipTypes();
    }

    @Override
    public boolean setRelationship(String currentUserName, String otherUserName, int relationshipType) {
        //check if both users exists and relationship type exists
        User u1 = getUser(currentUserName);
        User u2 = getUser(otherUserName);
        if (u1 == null || u2 == null) return false;
        
        // check if relationshiptype exists
        List<RelationshipType> types = DBConnection.getAllRelationshipTypes();
        boolean found = false;
        for(RelationshipType rt : types){
            if(rt.getId() == relationshipType)
                found = true;
        }
        if(!found) return false;
        
        return FacadeController.getInstance().setRelationship(currentUserName, otherUserName, relationshipType);
    }
    
    @Override
    public List<Relationship> getRelationships(String username) {
        //check if both users exists and relationship type exists
        User u1 = getUser(username);
        if (u1 == null) return null;
        
        return FacadeController.getInstance().getRelationships(username);
    }

    @Override
    public List<User> getAllUsersNotFriends(String username) {
        // invalid username
        if(username == null) return null;
        
        // nonexisting user
        if(DBConnection.getUser(username) == null) return null;
        
        return FacadeController.getInstance().getAllUsersNotFriends(username);
    }

    @Override
    public List<User> getHugs(String username) {
        // invalid username
        if(username == null) return null;
        
        // nonexisting user
        if(DBConnection.getUser(username) == null) return null;
        
        return FacadeController.getInstance().getHugs(username);
    }

    @Override
    public boolean giveHug(String fromUsername, String toUsername) {
        // check for existing hug
        List<User> huggers = DBConnection.getHugUsers(toUsername);
        boolean found = false;
        for(User u : huggers){
            if(u.getUsername().equals(fromUsername))
                found = true;
        }
        if(found) return false;
        
        // invalid values
        if(fromUsername == null || toUsername == null) return false;
        
        // invalid users
        if(fromUsername.equals(toUsername) || DBConnection.getUser(fromUsername) == null || DBConnection.getUser(toUsername) == null) return false;
        
        return FacadeController.getInstance().giveHug(fromUsername, toUsername);
    }

    @Override
    public boolean removeHugs(String username, List<User> users) {
        // invalid values
        if(username == null || users == null) return false;
        
        // nothing to do
        if(users.isEmpty()) return true;
        
        return FacadeController.getInstance().removeHugs(username, users);
    }

    @Override
    public boolean delete(String usernameToDelete, String admin, String password) {
        if(usernameToDelete == null || admin == null || password == null) return false;
        
        if(authenticateAdmin(admin,password))
        {
            return FacadeController.getInstance().delete(usernameToDelete, admin, password);
        }else{
            return false;
        }
            
    }
}
