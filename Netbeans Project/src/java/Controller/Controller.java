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
    
    public User getUser(String username);
    
    public User[] getAllUsers();
    
    public boolean createUser(String username, String password);
    
    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends);
            
}
