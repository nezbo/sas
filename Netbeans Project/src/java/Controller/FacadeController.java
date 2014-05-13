/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.DBConnection;
import Model.RelationshipType;
import Model.Relationship;
import Model.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
            int salt = DBConnection.getSalt(username);
            String hashPassword =hashPassword(salt,password);
            return DBConnection.validUserLogin(username, hashPassword);
        }
        catch(Exception e)
        {
            //no such user and cannot authenticate
            return false;
            //@TODO:missing proper errorhandling
        }
    }
    
    @Override
    public boolean authenticateAdmin(String username, String password) {
        try{
            int salt = DBConnection.getAdminSalt(username);
            String hashPassword = hashPassword(salt,password);
            
        return DBConnection.validAdminLogin(username, hashPassword);
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
    public User getUser(int id) {
        //@todo: handle bad user
        return DBConnection.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return DBConnection.getAllUsers();
    }

    @Override
    public boolean createUser(String username, String password) {
        try{
            //hash password
            //@Todo: use salts https://crackstation.net/hashing-security.htm#properhashing
            //for now:
            int salt = (int)(Math.random()*Integer.MAX_VALUE);
            String passwordHash = hashPassword(salt,password);
            
            password="";
            return DBConnection.createUser(username, passwordHash, salt);
            
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return false;
        }
    }
   
    @Override
    public boolean updateUserInfo(String oldUsername, String name, String address, String hobbies, String friends) {
        return DBConnection.updateUserInfo(oldUsername, name, address, hobbies, friends);
    }

    @Override
    public boolean updatePassword(String userName, String password) {
        try{
            int salt = (int)(Math.random()*Integer.MAX_VALUE);
            String passwordHash = hashPassword(salt,password);
            password = "";
        
            return DBConnection.updatePassword(userName, passwordHash, salt);
        
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return false;
        }
    }

    @Override
    public List<RelationshipType> getRelationShipTypes() {
        try{
            return DBConnection.getAllRelationshipTypes();
            
        }
        catch(Exception e)
        {
            return null;//need better error handling
        }
    }

    @Override
    public boolean setRelationship(String currentUserName, String addedFriendUserName, int relationshipType) {
        try{
            return DBConnection.setRelationship(currentUserName, addedFriendUserName, relationshipType);
            
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    @Override
    public List<Relationship> getRelationships(String username) {
        try {
            return DBConnection.getRelationshipsFromUser(username);
        }
        catch(Exception e)
        {
            // TODO: ERROR HANDLING
            return new ArrayList<Relationship>();
        }
    }

    @Override
    public List<User> getAllUsersNotFriends(String username) {
        try{
            
            return DBConnection.getAllUsersNotFriends(username);
            
        }
        catch(Exception e)
        {
            return new ArrayList<User>();
        }
    }

    @Override
    public List<User> getHugs(String username) {
        try{
        return DBConnection.getHugUsers(username);
        }
        catch(Exception e)
        {
            return new ArrayList<User>();
        }
    }

    @Override
    public boolean giveHug(String fromUsername, String toUsername) {
        try{
            return DBConnection.addHug(fromUsername, toUsername);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean removeHugs(String username, List<User> users) {
        try{
        return DBConnection.removeHugs(username, users);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean delete(String username) {
        try{                      
                return DBConnection.deleteUser(username);           
        }
        catch(Exception e)
        {
            System.err.println(e);
            return false;
        }
    }
    public String hashPassword(int salt, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((salt+password).getBytes("UTF-8"));
            return new String(hash);
    }
}
