/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import javax.faces.context.FacesContext;

/**
 *
 * @author dst
 */

@Named("SecurityBean")
@SessionScoped
public class SecurityBean implements java.io.Serializable {

    private String userName = "";
    private String password = "";
    private boolean authed = false;
    private String loginUserName = "";
    private boolean UserLogin = false;
    private boolean AdminLogin = false;
    private String logOut = "loggedOut";
    
    // password change
    private String newPassword = "";
    private String newPassword2 = "";
    
    public String logOut()
    {
       userName="";
       authed=false;
       AdminLogin = false;
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       return logOut;
    }
    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getUserName() {
        return userName;
    }

    /*public void setUserName(String userName) {
        if(!authed)
            this.userName = userName;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!authed)
            this.password = password;
    }
    
    // change password
    public String getNewPassword(){
        return newPassword;
    }
    
    public void setNewPassword(String newPw){
        this.newPassword = newPw;
    }
    
    public String getNewPassword2(){
        return newPassword2;
    }
    
    public void setNewPassword2(String newPw){
        this.newPassword2 = newPw;
    }
    
    // actions
    
    public String login()
    {
        authed = ControllerFactory.getController().authenticate(loginUserName,password);
        if(authed)
            userName=loginUserName;
        password = "";

        return authed ? "user" : "index";
    }
    
    public String adminLogin()
    {                
        authed = ControllerFactory.getController().authenticateAdmin(loginUserName,password);
        if(authed)
        {userName=loginUserName;
            AdminLogin=true;
        }
        
        password = "";
        return authed ? "login" : "adminLogin";
    }
    
    public void changePassword(){
        if(newPassword.length() > 0 && authed && newPassword.equals(newPassword2)){
            boolean result = ControllerFactory.getController().updatePassword(userName, newPassword);
            newPassword = "";
            newPassword2 = "";
            System.out.println(userName+ " password Changed: "+result);
        }
    }
    
    
    

    public boolean isLoggedIn(){
        return authed;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    boolean isAdmin() {
        return AdminLogin;
    }
}
