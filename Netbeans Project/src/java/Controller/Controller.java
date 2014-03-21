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
    
    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends);
            
}
