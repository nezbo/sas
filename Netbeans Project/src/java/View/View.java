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
        return false;
    }
    
    
}
