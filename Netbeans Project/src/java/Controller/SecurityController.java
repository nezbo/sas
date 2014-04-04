/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

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
    
    
    public Model.User loadUser(String Username, String currentUser)
    {
        return Model.ModelFactory.getUser(Username);
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
    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends) {
        // Check length of values
        if(username.length() > 31 
                || password.length() > 255 
                || name.length() > 255 
                || address.length() > 255 
                || friends.length() > 255)
            return false;
        
        // TODO SECURITY
        return FacadeController.getInstance().updateUser(username, password, name, address, hobbies, friends);
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
}
