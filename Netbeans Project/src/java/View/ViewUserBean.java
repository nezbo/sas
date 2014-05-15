/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.ControllerFactory;
import Model.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Emil
 */
@Named("ViewUserBean")
@RequestScoped
public class ViewUserBean implements java.io.Serializable {
    
    @ManagedProperty(value="#{SecurityBean}")
    private SecurityBean securityBean;
    @ManagedProperty(value="#{InformationBean}")
    private InformationBean informationBean;
    private boolean isMyself;
    private boolean isNotMyself;
    private String showUser;
    private String username="";

    private String name="";
    private String address="";
    private String hobbies="";
    
    public void getUser(String username){
        getUser(ControllerFactory.getController().getUser(username));
    }
    
    public void getUser(User user)
    {
        System.out.println(user);
        if(user!=null){
            if(!user.isExternal()){
                // update
                user = ControllerFactory.getController().getUser(user.getUsername());
                if(user!=null)
                {
                    if(user.getUsername().equals(securityBean.getUserName())){
                        this.setAddress(user.getAddress());
                    }

                    this.setName(user.getName());
                    this.setUsername(user.getUsername());

                    this.setHobbies(user.getHobbies());
                }
                else{//remove the user found before if no user is found
                    this.setName("");
                    this.setAddress("");
                    this.setHobbies("");
                }
            }else{ // external
                user = ControllerFactory.getController().getExternalUser(user.getKey());
                if(user != null){
                    this.setIsNotMyself(true);
                    this.setAddress(user.getAddress());
                    this.setName(user.getName());
                    this.setHobbies(user.getHobbies());
                }
            }
        }
    }
    
   public String goToFriend(User user)
   {
       System.out.println("going to: "+user);
       informationBean.setShowUser(user);
       return "user";
   }
    
    public boolean isIsNotMyself() {
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
    
    public InformationBean getInformationBean()
    {
        return informationBean;
    }
    
    public void setInformationBean(InformationBean informationBean)
    {
        this.informationBean = informationBean;
    }
    
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
        return name;
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
        return hobbies;
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
            ControllerFactory.getController().updateUserInfo(securityBean.getUserName(), name, address, hobbies, "");//@TODO: implement friends
            getSecurityBean().changePassword();
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
