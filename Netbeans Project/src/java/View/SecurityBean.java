/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import java.io.IOException;
import java.util.regex.Pattern;
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
    
    
     public void redirectIfNotLoggedInUser() throws IOException
    {
        if(!authed)
        {FacesContext.getCurrentInstance().getExternalContext().redirect("/ssasf14");        }
    }
    
    public void redirectIfNotLoggedInAdmin() throws IOException
    {
        if(!authed || !AdminLogin)
        {FacesContext.getCurrentInstance().getExternalContext().redirect("/ssasf14");        }
    }
    
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
        String error= validatePassword(userName, newPassword, newPassword2);
        if(error.equals(""))
        {
            if(newPassword.length() > 0 && authed && newPassword.equals(newPassword2)){
                boolean result = ControllerFactory.getController().updatePassword(userName, newPassword);
                newPassword = "";
                newPassword2 = "";
            }
        }
        else
        {
            //@TODO:output error to form
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
    
    public String validatePassword(String username, String password, String password2)
    {                
                Pattern p = Pattern.compile("(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*");//works a bit diferently than javascript. this works, javascript version does not : ^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$
                java.util.regex.Matcher m = p.matcher(password);
                
                 if (m.find()) {
                     if(password.equals(password2)){
                         if(!username.equals(password)){
                            //needs minimum 8 chars, capital letters, non-capital letters, numbers and special characters.
                            return "";
                            
                            
                         }
                         else
                             return "Username and password must not match";
                     }
                     else
                          return "The passwords typed in must match";
                }
                 else
                    return "Please input a password which contains, at least 8 character, capital and non-capital letters as well as numbers and symbols. Allowed symbols are !\\\"#¤%&/()=`^*_:;@£$€{<>\\\\+¨~";                                        
    }
            
}
