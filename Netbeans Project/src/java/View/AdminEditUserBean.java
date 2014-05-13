/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerFactory;
import Model.User;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Emil
 */
@Named("AdminEditUserBean")
@ViewScoped
public class AdminEditUserBean implements java.io.Serializable {

    @ManagedProperty(value = "#{SecurityBean}")
    private SecurityBean securityBean;
    @ManagedProperty(value = "#{InformationBean}")
    private InformationBean informationBean;
    @ManagedProperty(value = "#{param.id}")
    private String username;

    
    
    
    private String name = "";
    private String address = "";
    private String hobbies = "";
    
    private String newPassword = "";
    private String newPassword2 = "";

    public void getUser() {
        
        if (securityBean.isAdmin() && username != null) {
            User user = ControllerFactory.getController().getUser(username);
            if (user != null) {
                this.setAddress(user.getAddress());
                this.setName(user.getName());
                //this.setUsername(user.getUsername());

                this.setHobbies(user.getHobbies());
            } /*else {//remove the user found before if no user is found
                username = "";
                this.setName("");
                this.setAddress("");
                this.setHobbies("");
            }*/
            
            //perhaps show a no user page?
        }
    }

    public String goToFriend(User user) {
        informationBean.setShowUser(user);
        return "user";
    }

    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean SecurityBean) {
        this.securityBean = SecurityBean;
    }

    public InformationBean getInformationBean() {
        return informationBean;
    }

    public void setInformationBean(InformationBean informationBean) {
        this.informationBean = informationBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username!=null)
        this.username = username;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    /**
     * saves the data on user page
     *
     * @return
     */
    public String save() {
        ControllerFactory.getController().updateUserInfo(username, name, address, hobbies, "");//@TODO: implement friends
        changePassword();
        return "user";//missing some more security handling
    }

    public void changePassword(){
        if(newPassword.length() > 0 && securityBean.isAdmin() && newPassword.equals(newPassword2)){
            boolean result = ControllerFactory.getController().updatePassword(username, newPassword);
            newPassword = "";
            newPassword2 = "";
            System.out.println(username+ " password Changed: "+result);
        }
    }
}
