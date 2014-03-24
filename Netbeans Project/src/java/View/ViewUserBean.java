/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.ControllerFactory;
import Model.User;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author Emil
 */
@Named("ViewUserBean")
@SessionScoped
public class ViewUserBean implements java.io.Serializable {
    
    @ManagedProperty(value="#{SecurityBean}")
    private SecurityBean securityBean; // +setter
    private boolean isMyself;
    private boolean isNotMyself;
    private String showUser;
    private String username="";

    private String name="";
    private String address="";
    private String hobbies="";   
    
    public void getUser(String userName)
    {        
        if(userName!=null){
        User user = ControllerFactory.getController().getUser(userName);
        if(user!=null)
        {
            this.setName(user.getName());
            this.setAddress(user.getAddress());
            this.setHobbies(user.getHobbies());
        }
        
    }
    }
    
    public boolean isIsNotMyself() {                
        isNotMyself = !this.isIsMyself();
        return isNotMyself;
    }

    public void setIsNotMyself(boolean isNotMyself) {
        this.isNotMyself = isNotMyself;
    }
    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean SecurityBean) {
        this.securityBean = SecurityBean;
    }
    
    
/*    public boolean getisMyself(){
        // look at loginBean.isLoggedIn() and check username matches
        
            
        
        return true;                      
    }
  */
    
    public boolean isIsMyself()
    {
        isMyself=(securityBean.getUserName()!=null && !securityBean.getUserName().equals(""));
        return isMyself;
    }

    public void setIsMyself(boolean isMyself) {
        this.isMyself = isMyself;
    }

    public String getUsername() {
       if(this.isIsMyself())
        return username;
       return "";
    }

    public void setUsername(String username) {
        
        this.username = username;
        
    }

    public String getName() {
        if(this.isIsMyself())
        return name;
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        if(this.isIsMyself())
        return address;
        return "";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobbies() {
        if(this.isIsMyself())
        return hobbies;
        return "";
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getShowUser() {
        return showUser;
    }

    public void setShowUser(String showUser) {
        this.showUser = showUser;
    }

    /**
     * saves the data on user page
     * @return 
     */
    public String save()
    {
        if(this.isIsMyself())
        {    
            //ControllerFactory.getController().updateUser("dante", securityBean.getUserName(), name, address, hobbies, "");
            
            ControllerFactory.getController().updateUserInfo(securityBean.getUserName(), name, address, hobbies, "");//@TODO: implement friends
        }
        
        return "user";//missing some more security handling
    }
    
    /**
     * initites search for another user - might need to be placed in other bean, but this was easiest.
     * @return 
     */
    public String search()
    {
        this.getUser(this.getShowUser());
        return "search";
    }
    
    
}
