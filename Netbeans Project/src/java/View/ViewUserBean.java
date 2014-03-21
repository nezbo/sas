/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

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
    
    @ManagedProperty(value="#{loginBean}")
    private SecurityBean loginBean; // +setter
    
    private String username;
    private String name;
    private String address;
    private String hobbies;

    public String getUsername() {
        return username;
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
        return address;
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
    
    public boolean isMyself(){
        // look at loginBean.isLoggedIn() and check username matches
        return false;
    }
}
