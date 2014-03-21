/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

import Controller.ControllerFactory;

/**
 *
 * @author Emil
 */
@Named("CreateBean")
@SessionScoped
public class CreateBean implements java.io.Serializable {
    
    @ManagedProperty(value="#{loginBean}")
    private SecurityBean loginBean; // +setter
    
    private String username;
    private String password;
    private String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String createUser(){
        boolean result = ControllerFactory.getController().createUser(username, password);
        if(result){
            loginBean.setUserName(username);
            loginBean.setPassword(password);
            loginBean.login();
            return "user.xhtml?user="+username;
        }
        return "index";
    }
}
