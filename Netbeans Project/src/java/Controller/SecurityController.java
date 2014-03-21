/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.User;

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
    public User getUser(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User[] getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
