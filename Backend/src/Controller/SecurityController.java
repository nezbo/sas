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
public class SecurityController implements Controller {
    
    private static SecurityController Controller = null;

    
    
    public static SecurityController getInstance()
    {
        if(Controller==null)
            Controller = new SecurityController();
        return Controller;        
    }
    
    
    public Model.User loadUser(String Username, String currentUser)
    {
        return Model.ModelFactory.getUser(Username);
    }
}
