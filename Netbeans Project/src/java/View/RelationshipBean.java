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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author dst
 */

@Named("RelationshipBean")
@RequestScoped
public class RelationshipBean implements java.io.Serializable {
     // Properties
     @ManagedProperty(value="#{SecurityBean}")
     private SecurityBean securityBean; 
     @ManagedProperty(value="#{InformationBean}")
     private InformationBean informationBean; 
     
     // Hugs
     private List<User> usersWhoHuggedMe;
    
     // Users
     private List<User> currentListOfUsers;
     private List<User> currentListOfUsersNotFriends;
     
     // Relationships
     private List<Relationship> relationships;          // All relationship this user has to other users
     private Map<String, Object> relationshipTypes;
     private String selectedRelationshipType;

     /**
      * Adds a hug from the current user to 'user'
      * @param user
      * @return Refreshes the page when terminating
      */
     public String hug(User user)
     {
         if(ControllerFactory.getController().giveHug(securityBean.getUserName(), user.getUsername()));
         {
             ArrayList<User> l = new ArrayList<User>();
             l.add(user);
             // TODO: REMOVE A HUG YOU HAVE "RE-HUGGED"
             //ControllerFactory.getController().removeHugs(securityBean.getUserName(), l); //wanna delete it but does not work
         }
         return "user";
     }
     
     /**
      * Get a list of all users who have hugged me
      * @return 
      */
     public List<User> getUsersWhoHuggedMe() 
     {
         usersWhoHuggedMe = ControllerFactory.getController().getHugs(securityBean.getUserName());
         if (usersWhoHuggedMe == null){
             // TODO: HANDLE THIS CASE
             usersWhoHuggedMe = new ArrayList<User>();
         }
         
         return usersWhoHuggedMe;
     }
     
     public void setUsersWhoHuggedMe(List<User> usersWhoHuggedMe) 
     {
         this.usersWhoHuggedMe = usersWhoHuggedMe;
     }
     
     /**
      * Get a list of all users I have a relationship with
      * @return 
      */
    public List<Relationship> getRelationships() {
        
        relationships = ControllerFactory.getController().getRelationships(securityBean.getUserName());
        if (relationships == null){
            //TODO: HANDLE THIS CASE
            relationships = new ArrayList<>();
        }
        
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }
    
     /**
     * Get the current list users not already in a relationship with 
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
