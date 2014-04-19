/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;


import javax.inject.Named;
import Controller.ControllerFactory;
import Model.Relationship;
import Model.RelationshipType;
import Model.User;
import com.sun.faces.util.CollectionsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
     private List<User> currentListOfUsersNotFriends;
     private List<Relationship> relationships;
     private Map<String, Object> relationshipTypes;
     private String selectedRelationshipType;
     private List<User> currentListOfUsersInRelationshipWith;

     public String hug(User user)
     {
         if(ControllerFactory.getController().giveHug(securityBean.getUserName(), user.getUsername()));
         {
             ArrayList<User> l = new ArrayList<User>();
             l.add(user);
             //ControllerFactory.getController().removeHugs(securityBean.getUserName(), l); //wanna delete it but does not work
         }
         return "user";
     }
     
    public List<User> getCurrentListOfUsersInRelationshipWith() {
        
        currentListOfUsersInRelationshipWith = ControllerFactory.getController().getHugs(securityBean.getUserName());
        if(currentListOfUsersInRelationshipWith.size()>0)
        {
            //ControllerFactory.getController().removeHugs(securityBean.getUserName(), currentListOfUsersInRelationshipWith);//seen hugs, now remove them, maybe wait with this
        }
        return currentListOfUsersInRelationshipWith;
    }

    public void setCurrentListOfUsersInRelationshipWith(List<User> currentListOfUsersInRelationshipWith) {
        this.currentListOfUsersInRelationshipWith = currentListOfUsersInRelationshipWith;
    }
    
     
     
     /**
     * gets the current list users not allready in a relationship with 
     * @return 
     */
    public List<User> getCurrentListOfUsersNotFriends() {
        
        if(securityBean.isLoggedIn() && currentListOfUsersNotFriends==null)
        {
             currentListOfUsersNotFriends = ControllerFactory.getController().getAllUsersNotFriends(securityBean.getUserName());
        }
        if(securityBean.isLoggedIn())
            return currentListOfUsersNotFriends;
        else
            return new ArrayList<User>();
    }
    
    public Map<String, Object> getRelationshipTypes()
    {
        if (relationshipTypes == null){
            relationshipTypes = new HashMap<String, Object>();
            for(RelationshipType type : ControllerFactory.getController().getRelationShipTypes())
                relationshipTypes.put(type.getType(), type);
        }
        System.out.println(relationshipTypes.size());
        return relationshipTypes;
    }
    
    public String getSelectedRelationshipType()
    {
        return selectedRelationshipType;
    }

    public void setCurrentListOfUsersNotFriends(List<User> currentListOfUsers) {
        this.currentListOfUsersNotFriends = currentListOfUsersNotFriends;
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
        if(ControllerFactory.getController().setRelationship(securityBean.getUserName(), user.getUsername(),  1))
        {
            return "added";
        }
        else
            return securityBean.getUserName()+user.getUsername();

//if(securityBean.isLoggedIn()){
        
            //ControllerFactory.getController().addRelationShip(securityBean.getUserName(), informationBean.getCurrentListOfUsers().get(id), )
        
    }
    
    
}
