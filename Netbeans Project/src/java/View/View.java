/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;
import Model.ModelFactory;


/**
 * single point of entry to control layer
 * @author dst
 */
public class View {
     private static View controller = null;

    private View() {
        
    }
    
    public static View getInstance()
    {
        if(controller==null)
            controller = new View();
        return controller;        
    }
    public boolean login(String userName, String password)
    {
        // TODO: Dummy code to see if it works, this should ask the 
        // SecurityController for logging in (which should query the Model)
        return userName.equals("admin") && password.equals("admin");
    }
    
    public boolean createUser(String userName, String password, String repPassword){
        if(!password.equals(repPassword)) return false;
        
        // TODO: Dummy code to see if it works, this should ask the
        // SecurityController for a sucessful creation and (somehow) rely any
        // problems back to the UI.
        return true;
    }
}
