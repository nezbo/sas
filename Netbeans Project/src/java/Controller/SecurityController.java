/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

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
        // TODO SECURITY
         if(username.length() <= 31 
                && password.length() <= 255 ) {
              return FacadeController.getInstance().authenticate(username, password);
        } else {
             return false;
        }
       
    }
   
    @Override
    public boolean authenticateAdmin(String username, String password) {
        // TODO: Security
        if(username.length() <= 31 && password.length() <= 255) {
            return FacadeController.getInstance().authenticateAdmin(username, password);
        } else {
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        // TODO SECURITY
        return FacadeController.getInstance().getUser(username);
    }

    @Override
    public List<User> getAllUsers() {
        // TODO SECURITY
        return FacadeController.getInstance().getAllUsers();
    }

    @Override
    public boolean createUser(String username, String password) {
        // TODO SECURITY
        if(username.length()>252 ||password.length()>252) //dont want too large names into database
            return false;
        return FacadeController.getInstance().createUser(username, password);
    }

    @Override
    public boolean updatePassword(String username, String password) {
        // Check length of values
        if(username.length() > 31 || password.length() > 255)
            return false;
        
        // TODO SECURITY
        return FacadeController.getInstance().updatePassword(username, password);
    }

    @Override
    public boolean updateUserInfo(String username, String name, String address, String hobbies, String friends) {
        if(name==null 
                || address==null 
                || friends==null
                || hobbies==null)
            return false;
        
        if(name.length() > 255 
                || address.length() > 255 
                || friends.length() > 255)
            return false;
        
        // TODO SECURITY
        return FacadeController.getInstance().updateUserInfo(username, name, address, hobbies, friends);
    }

    @Override
    public List<RelationshipType> getRelationShipTypes() {
            return FacadeController.getInstance().getRelationShipTypes();
    }

    @Override
    public boolean setRelationship(String currentUserName, String otherUserName, int relationshipType) {
        //check if both users exists and relationship type exists
        return FacadeController.getInstance().setRelationship(currentUserName, otherUserName, relationshipType);
    }
    
    @Override
    public List<Relationship> getFriends(String username) {
        //check if username exists?
        return FacadeController.getInstance().getFriends(username);
    }

    @Override
    public List<User> getAllUsersNotFriends(String username) {
        //perhaps check if username exists?
       return FacadeController.getInstance().getAllUsersNotFriends(username);
    }

    @Override
    public List<User> getHugs(String username) {
        return FacadeController.getInstance().getHugs(username);
    }

    @Override
    public boolean giveHug(String fromUsername, String toUsername) {
        return FacadeController.getInstance().giveHug(fromUsername, toUsername);
    }

    @Override
    public boolean removeHugs(String username, List<User> users) {
        return FacadeController.getInstance().removeHugs(username, users);
    }

    @Override
    public boolean delete(String usernameToDelete, String admin, String password) {
        if(authenticateAdmin(admin,password))
        {
            return FacadeController.getInstance().delete(usernameToDelete, admin, password);
        }else{
            return false;
        }
            
    }
}
