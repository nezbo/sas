/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

/**
 * Makes security checks before forwarding data to the same method in the facade class
 * @author dst
 */
public class SecurityController {
    
    private static SecurityController controller = null;

    private SecurityController() {
        
    }
    
    public static SecurityController getInstance()
    {
        if(controller==null)
            controller = new SecurityController();
        return controller;        
    }
    
    
    public Model.User loadUser(String Username, String currentUser)
    {
        return Model.ModelFactory.getUser(Username);
    }
}
