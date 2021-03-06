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
import Model.Relationship;
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
     * deletes a user, only admins are allowed to use this function
     * @param username
     * @return 
     */
    public boolean delete(String username);
    /**
     * Checks if a username and password is a valid admin account
     * It is assumed that you come from the adminLogin page when calling this method
     * @param username The admin username
     * @param password The matching password
     * @return  True if the username exists as an admin and has the given password
     */
    public boolean authenticateAdmin(String username, String password);
    
    /**
     * Gets a specific user's profile data (address, hobbies, etc)
     * @param username The wanted username.
     * @return A User object with the information from the database.
     */
    public User getUser(String username);
    
    /**
     * Gets a specific user's profile data (address, hobbies, etc)
     * @param id The id of the wanted user.
     * @return A User object with the information from the database.
     */
    public User getUser(int id);
    
    /**
     * Fetches a user from the external API of Group 5 with the
     * given key.
     * @param key The unique identifier of the user.
     * @return A complete user object (relative to their values)
     */
    public User getExternalUser(String key);
    
    /**
     * Gets information for all users in the database, for searching etc.
     * @return An array of User objects.
     */
    public List<User> getAllUsers();
    
    /**
     * Fetches all the (shallow) users from external API of Group 5.
     * @return A list of users, missing hobbies
     */
    public List<User> getExternalUsers();
    
    public List<Relationship> getRelationships(String username);
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
    
    public boolean removeRelationship(String currentUsername, String otherUsername);
    
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
