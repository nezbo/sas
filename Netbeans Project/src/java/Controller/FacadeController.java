/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.User;

/**
 * Serves as the entry point to all other controllers, for the view class, through SecurityController
 * @author dst
 */
public class FacadeController implements Controller {

    private static FacadeController controller = null;

    private FacadeController() {
        
    }
    
    protected static Controller getInstance()
    {
        if(controller==null)
            controller = new FacadeController();
        return controller;        
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
