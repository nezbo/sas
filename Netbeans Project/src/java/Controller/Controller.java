/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Defines the overall functions for Security controller and facade controller
 */
package Controller;
import Model.User;

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
     * Gets a specific user's profile data (address, hobbies, etc)
     * @param username The wanted username.
     * @return A User object with the information from the database.
     */
    public User getUser(String username);
    
    /**
     * Gets information for all users in the database, for searching etc.
     * @return An array of User objects.
     */
    public User[] getAllUsers();
    
    public boolean createUser(String username, String password);
    
    public boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends);
    
    public boolean updatePassword(String userName, String password);
            
}
