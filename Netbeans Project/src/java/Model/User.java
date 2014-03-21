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
        private String username;
        private String address;
        private String hobbies;

    protected User(String name, String username, String address, String hobbies) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.hobbies = hobbies;
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
}
