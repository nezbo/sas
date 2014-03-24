/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.DBConnection;
import Model.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Serves as the entry point to all other controllers, for the view class, through SecurityController
 * @author dst
 */
public class FacadeController implements Controller {

    private static FacadeController controller = null;

    private FacadeController() {}
    
    protected static Controller getInstance()
    {
        if(controller==null)
            controller = new FacadeController();
        return controller;        
    }

    @Override
    public boolean authenticate(String username, String password) {
        try{
        String hashPassword = password;//hashPassword(password);
        return DBConnection.validUserLogin(username, hashPassword);
        }
        catch(Exception e)
        {
            return false;
            //@TODO:missing proper errorhandling
        }
    }
    
    @Override
    public User getUser(String username) {
        //@todo: handle bad user
        return DBConnection.getUser(username);
    }

    @Override
    public User[] getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createUser(String username, String password) {
        try{
            //hash password
            //@Todo: use salts https://crackstation.net/hashing-security.htm#properhashing
            //for now:
           String passwordHash = password;//hashPassword(password);
            
            password="";
        return DBConnection.createUser(username, passwordHash);
        }
        catch(Exception e)
        {//@TODO: : missisng proper errorhandling
            return false;
        }                
    }
    public String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return new String(hash);
    }
    @Override
    public boolean updateUser(String oldUsername, String password, String name, String address, String hobbies, String friends) {
        return DBConnection.updateUser(oldUsername, password, name, address, hobbies, friends);
    }

    @Override
    public boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends) {
        return DBConnection.updateUserInfo(userName, name, address, hobbies, friends);
    }
    
}
