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

    private String addFriendRelationshipType ="";
    private String addFriendRelationshipTypePerson="";
    private int addFriendRelationshipTypePersonID;

    
    private String showUser="";
    

    public String getAddFriendRelationshipTypePerson() {
        return addFriendRelationshipTypePerson;
    }

    public void setAddFriendRelationshipTypePerson(String addFriendRelationshipTypePerson) {
        this.addFriendRelationshipTypePerson = addFriendRelationshipTypePerson;
    }

    public int getAddFriendRelationshipTypePersonID() {
        return addFriendRelationshipTypePersonID;
    }

    public void setAddFriendRelationshipTypePersonID(int addFriendRelationshipTypePersonID) {
        this.addFriendRelationshipTypePersonID = addFriendRelationshipTypePersonID;
    }

    
    public String getAddFriendRelationshipType() {
        return addFriendRelationshipType;
    }

    public void setAddFriendRelationshipType(String addFriendRelationshipType) {
        this.addFriendRelationshipType = addFriendRelationshipType;
    }

    
    
    public String getShowUser() {
        return showUser;
    }

    public void setShowUser(String showUser) {
        this.showUser = showUser;
    }

    
   
}
