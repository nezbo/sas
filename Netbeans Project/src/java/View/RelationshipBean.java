/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;


import javax.inject.Named;
import Controller.ControllerFactory;
import Model.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

/**
 *
 * @author dst
 */

@Named("RelationshipBean")
@RequestScoped
public class RelationshipBean implements java.io.Serializable {
    
     @ManagedProperty(value="#{SecurityBean}")
    private SecurityBean securityBean; 
     @ManagedProperty(value="#{InformationBean}")
    private InformationBean informationBean; 
     private List<User> currentListOfUsers;

    public List<User> getCurrentListOfUsers() {
        
        if(securityBean.isLoggedIn() && currentListOfUsers==null)
        {
             currentListOfUsers = ControllerFactory.getController().getAllUsers();             
        }
        if(securityBean.isLoggedIn())
            return currentListOfUsers;
        else
            return new ArrayList<User>();
    }

    public void setCurrentListOfUsers(List<User> currentListOfUsers) {
        this.currentListOfUsers = currentListOfUsers;
    }
     

     
     public void InitializeFriends()
     {
        
     }
     
     
    
     
    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean securityBean) {
        this.securityBean = securityBean;
    }

    public InformationBean getInformationBean() {
        return informationBean;
    }

    public void setInformationBean(InformationBean informationBean) {
        this.informationBean = informationBean;
    }
    
    
    
    
    
    public String addRelationShip(User user)
    {        
        if(ControllerFactory.getController().addRelationship(securityBean.getUserName(), getCurrentListOfUsers().get(informationBean.getAddFriendRelationshipTypePersonID()).getUsername(),  1))
        {
            return "added";
        }
        else
            return "notAdded";

//if(securityBean.isLoggedIn()){
        
            //ControllerFactory.getController().addRelationShip(securityBean.getUserName(), informationBean.getCurrentListOfUsers().get(id), )
        
    }
    
    
}
