/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;

/**
 *
 * @author dst
 */
@Named("SecurityBean")
@SessionScoped
public class SecurityBean implements java.io.Serializable {

    private String userName = "";
    private String password = "";
    private boolean authed = false;
    
    private boolean UserLogin = false;
    private boolean AdminLogin = false;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(!authed)
            this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!authed)
            this.password = password;
    }
    
    public String login()
    {
        // TODO: Dummy code to see if it works, this should ask the 
        // SecurityController for logging in (which should query the Model)
        authed = ControllerFactory.getController().authenticate(userName,password);
        password = "";
        return authed ? "user" : "index";
    }
    
    public boolean isLoggedIn(){
        return authed;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
