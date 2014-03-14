/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author dst
 */
@Named("SecurityBean")
@SessionScoped
public class SecurityBean implements java.io.Serializable {

    private String name = "Hello";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
