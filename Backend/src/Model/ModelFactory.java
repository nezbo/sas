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
public class ModelFactory {
    
    
    
    /**
     * Returns a specific user based on username
     * @return 
     */
    public static User getUser(String username)
    {
        return new User(null, username, null, null);
    }
}
