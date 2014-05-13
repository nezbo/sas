/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author dst
 */
public class User {
        private String name;
        private int id = -1;
        private String username;
        private String address;
        private String hobbies;
        
        private boolean isExternal = false;
        private String externalKey = null;

    public User(String name, String username, String address, String hobbies) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.hobbies = hobbies;
    }
    
    public User(String name, String username, String address, String hobbies, int id) {
        this(name,username,address,hobbies);
        this.id =id;
    }
    
    /**
     * Initializes the User from an external API.
     * @param name The name
     * @param username The username
     * @param address The address
     * @param hobbies The hobbies
     * @param key The external key of this user
     */
    public User(String name, String username, String address, String hobbies, String key){
        this(name,username,address,hobbies);
        
        this.externalKey = key;
        this.isExternal = true;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getUsername() {  
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getHobbies() {
        return hobbies;
    }
    
    public String getKey(){
        return externalKey;
    }
    
    public boolean isExternal(){
        return isExternal;
    }
    
    public String toString(){
        return username + ": ["+name+", "+address+", "+hobbies+"]";
    }
}
