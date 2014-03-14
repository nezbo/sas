/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author dst
 */
@Named("SecurityBean")
@SessionScoped
public class SecurityBean implements java.io.Serializable {

    private String userName = "";
    private String password = "";
    
    private boolean UserLogin = false;
    private boolean AdminLogin = false;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String login()
    {
        return View.getInstance().login(userName, password) ? "user" : "index";
    }
    
    
    

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
