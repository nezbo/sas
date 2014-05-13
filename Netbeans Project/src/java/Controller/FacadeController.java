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
import View.RelationshipBean;
import View.ViewUserBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
    public boolean removeRelationship(String currentUsername, String otherUsername)
    {
        try {
            return DBConnection.deleteRelationship(currentUsername, otherUsername);
        }
        catch (Exception e)
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
    
    @Override 
    public List<User> getExternalUsers(){
        try{
            JSONObject all_external = httpGetJSON("https://192.237.211.45/service/users/");
            ArrayList<User> result = new ArrayList<User>();

            JSONArray arr = all_external.getJSONArray("users");
            for(int i = 0; i < arr.length(); i++){
                String user = arr.getString(i);
                result.add(new User(user,user,"<no address>","<no hobbies>",user));
            }
            return result;
        }catch(JSONException ex){
            System.out.println(ex);
        }
        return null;
    }
    
    @Override
    public User getExternalUser(String key){
            try {
            JSONObject json = httpGetJSON("https://192.237.211.45/service/users/"+key);
            JSONArray hobbies = json.getJSONArray("hobbies");
            String sHobbies = "";
            for(int i = 0; i < hobbies.length(); i++){
                sHobbies += hobbies.getString(i)+"\n";
            }
            User newUser = new User(key,key,"",sHobbies,key);
            
            return newUser;
                    
        } catch (JSONException ex) {
            Logger.getLogger(ViewUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String hashPassword(int salt, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((salt+password).getBytes("UTF-8"));
            return new String(hash);
    }
    
    // PRIVATE HELPER METHODS
    
    private JSONObject httpGetJSON(String string_url){
        try {
            // ssl bypassing
            TrustManager trm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                
                
            };
            
            HostnameVerifier allHostsValid = new HostnameVerifier(){
                @Override
                public boolean verify(String string, SSLSession ssls) {
                    return true; // all good
                }
            };
             
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { trm }, new java.security.SecureRandom());
            //HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // apply ssl bypassing only on current request
            URL url = new URL(string_url);
            URLConnection conn = url.openConnection();
            HttpsURLConnection httpsconn = (HttpsURLConnection)conn;
            httpsconn.setSSLSocketFactory(sc.getSocketFactory());
            httpsconn.setHostnameVerifier(allHostsValid);
            
            // actual http get
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\Z").next();
            JSONObject json = new JSONObject(response);
            scanner.close();

            return json;
             
        } catch ( NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(RelationshipBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            System.out.println("EX:"+ex);
        } catch ( IOException | JSONException ex) {
            System.out.println("EX:"+ex);
        }
        // something went wrong, nothing to get
        return null;
    }
}
