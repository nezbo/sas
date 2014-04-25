/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Defines the overall functions for Security controller and facade controller
 */
package Controller;
import Model.RelationshipType;
import Model.User;
import java.util.List;

/**
 *
 * @author dst
 */
public interface Controller {
    
    /**
     * Checks if a username and password matches
     * @param username The username
     * @param password The matching password
     * @return True if the username exists and has the given password
     */
    public boolean authenticate(String username, String password);
    
     /**
     * Checks if a username and password matches to admin
     * @param username The username
     * @param password The matching password
     * @return True if the username exists and has the given password
     */
    public boolean authenticateAdmin(String username, String password);
    
    
    /**
     * deletes a user, only admins are allowed to use this function
     * @param username
     * @return 
     */
    public boolean delete(String usernameToDelete, String admin, String password);
    /**
     * Gets a specific user's profile data (address, hobbies, etc)
     * @param username The wanted username.
     * @return A User object with the information from the database.
     */
    public User getUser(String username);
    
    /**
     * Gets information for all users in the database, for searching etc.
     * @return An array of User objects.
     */
    public List<User> getAllUsers();
    public List<User> getAllUsersNotFriends(String username);
    public boolean createUser(String username, String password);
    
    public boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends);
    
    public boolean updatePassword(String userName, String password);
    
    /**
     * Returns a list of relationship types availble.
     * @return 
     */
    public List<RelationshipType> getRelationShipTypes();
    
    public boolean setRelationship(String currentUserName, String otherUserName, int relationshipType);
    
    
    /**
     * gets the hugs for a specific user
     * @param username
     * @return 
     */
    public List<User> getHugs(String username);
    
    /**
     * gives a hug to a specific user.
     * @param fromUsername
     * @param toUsername
     * @return 
     */
    public boolean giveHug(String fromUsername, String toUsername);
    
    /**
     * Removes hugs from the database.
     * Used in reference when hugs are recieved
     */
     public boolean removeHugs(String username, List<User> users);
     
}
