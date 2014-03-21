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
        //private String userName; // This information is not public
        private String address;
        private String hobbies;

    protected User(String name, String userName, String address, String hobbies) {
        this.name = name;
        //this.userName = userName;
        this.address = address;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    /*public String getUserName() {  
        return userName;
    }*/

    public String getAddress() {
        return address;
    }

    public String getHobbies() {
        return hobbies;
    }
}
