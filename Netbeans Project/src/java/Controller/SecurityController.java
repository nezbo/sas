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
    public boolean authenticate(String username, String password) {
        // TODO SECURITY
        return FacadeController.getInstance().authenticate(username, password);
    }

    @Override
    public User getUser(String username) {
        // TODO SECURITY
        return FacadeController.getInstance().getUser(username);
    }

    @Override
    public User[] getAllUsers() {
        // TODO SECURITY
        return FacadeController.getInstance().getAllUsers();
    }

    @Override
    public boolean createUser(String username, String password) {
        // TODO SECURITY
        return FacadeController.getInstance().createUser(username, password);
    }

    @Override
    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends) {
        // TODO SECURITY
        return FacadeController.getInstance().updateUser(username, password, name, address, hobbies, friends);
    }
}
