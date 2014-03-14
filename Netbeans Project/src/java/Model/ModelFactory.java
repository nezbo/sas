/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dst
 */
public class ModelFactory {
    
    private static HashMap<String, User> loadedUsers = new HashMap<String, User>();
    
    /**
     * Returns a specific user based on username
     * @return 
     */
    public static User getUser(String username)
    {
        if (!loadedUsers.containsKey(username))
        {
            ResultSet rs = null;
            String name = ResultSetGetString(rs, "name");
            String adress = ResultSetGetString(rs, "adress");
            String hobbies = ResultSetGetString(rs, "hobbies");
            User user = new User(name, username, adress, hobbies); //TODO: Load user from database
            loadedUsers.put(username, user);
        }

        return loadedUsers.get(username);
    }
    
    private static String ResultSetGetString(ResultSet rs, String s)
    {
        try
        {
            return rs.getString(s);
        }
        catch (SQLException e)
        {
            return "FUUUUUUCK"; //TODO: Remove FUUUUUUUCK
        }
    }
}
