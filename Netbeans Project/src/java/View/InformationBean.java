/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import Model.User;
import java.util.List;

/**
 *
 * @author dst
 */

@Named("InformationBean")
@SessionScoped
public class InformationBean implements java.io.Serializable {

    private String showUser="";
    private List<User> currentListOfUsers;

    public List<User> getCurrentListOfUsers() {
        return currentListOfUsers;
    }

    public void setCurrentListOfUsers(List<User> currentListOfUsers) {
        this.currentListOfUsers = currentListOfUsers;
    }
    
    
    public String getShowUser() {
        return showUser;
    }

    public void setShowUser(String showUser) {
        this.showUser = showUser;
    }

    
   
}
