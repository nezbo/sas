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
 * Holds administrative functions as delete and edit user
 *
 * @author dst
 */
@Named("AdminBean")
@RequestScoped
public class AdminBean implements java.io.Serializable {

    @ManagedProperty(value = "#{SecurityBean}")
    private SecurityBean securityBean;
    @ManagedProperty(value = "#{InformationBean}")
    private InformationBean informationBean;
    private List<User> currentListOfUsers;
    private String password;

    //<editor-fold desc="beans">
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

    //</editor-fold>
    public List<User> getCurrentListOfUsers() {
        currentListOfUsers = ControllerFactory.getController().getAllUsers();
        return currentListOfUsers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String delete(User user) {
        if (securityBean.isLoggedIn()) {
            if (ControllerFactory.getController().delete(user.getUsername(), securityBean.getUserName(), password)) {
                return "usersTrue";
            }
        }
        return "usersFalse";
    }

    public String edit(User user) {
        return "editUser";
    }
}
