/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

/**
 * Serves as the entry point to all other controllers, for the view class, through SecurityController
 * @author dst
 */
public class FacadeController implements Controller {
    private static FacadeController Controller;
    
    
    public static Controller getInstance()
    {
        if(Controller==null)
             Controller = new FacadeController();
        return Controller;
    }
}
