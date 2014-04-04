    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Model.RelationshipType;
import Model.User;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author marcher89
 */
public class DBConnection {

    private static Map<String, String[]> roles = new HashMap<>();
    private static String url;
    private static int port;
    private static String database;

    @Resource(name = "jdbc/login")
    private static DataSource loginDatasource;
    @Resource(name = "jdbc/user")
    private static DataSource userDatasource;
    @Resource(name = "jdbc/admin")
    private static DataSource adminDatasource;

    private static Connection loginConnection;
    private static Connection userConnection;
    private static Connection adminConnection;

    private static final Map<String, PreparedStatement> preparedStmts = new HashMap<>();

    /* "Constructor" */
    static {
        loadXML();
        /*try {
         Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException ex) {
         //TODO: Error handling
         ex.printStackTrace();
         }*/
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(url);
        System.out.println(port);
        System.out.println(database);
        System.out.println(validUserLogin("usder", "password"));
        System.out.println(validUserLogin("user", "pasdsword"));
        System.out.println(validUserLogin("user", "password"));
        System.out.println(updatePassword("bingo", "password2"));
        System.out.println(getUser("user"));
        for (RelationshipType rt : getAllRelationshipTypes()) {
            System.out.println("RelationshipType: [id: "+rt.getId()+", type: "+rt.getType()+"]");
        }
    }

    public static boolean validUserLogin(String username, String password) {
        try {
            PreparedStatement stmt = getPreparedStatement("SELECT COUNT(*) AS `count` FROM `sassy`.`User`  WHERE `username` = ? AND `password` = MD5(?);", getLoginConnection());
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeQuery();
            stmt.getResultSet().next();
            int count = stmt.getResultSet().getInt(1);
            return count > 0;
        } catch (SQLException ex) {
            //Unable to create prepared statement
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean createUser(String username, String cleartextPassword) {
        try {
            PreparedStatement stmt = getPreparedStatement("INSERT INTO  `sassy`.`User` (`username`,`password`,`name`,`address`,`hobbies`) VALUES ( ?, MD5( ? ) ,  '',  '',  '');", getLoginConnection());
            stmt.setString(1, username);
            stmt.setString(2, cleartextPassword);
            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            //TODO: Error handling
            return false;
        }
    }

    public static boolean updatePassword(String username, String cleartextPassword) {
        try {
            PreparedStatement stmt = getPreparedStatement("UPDATE `sassy`.`User` SET `password` = MD5(?) WHERE `username` = ?;", getUserConnection());
            stmt.setString(1, cleartextPassword);
            stmt.setString(2, username);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //TODO: Error handling
            return false;
        }
    }

    public static boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends) {

        try {
            PreparedStatement stmt = getPreparedStatement("UPDATE `sassy`.`User` SET `name` = ?,`address` = ?,`hobbies` = ? WHERE `username` = ?;", getUserConnection());
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, hobbies);
            stmt.setString(4, userName);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //TODO: Error handling
            return false;
        }

    }

    public static User getUser(String username) {
        try {
            PreparedStatement stmt = getPreparedStatement("SELECT * FROM `sassy`.`User` WHERE `username` = ?;", getUserConnection());

            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();
            if (set.first())//check if exists            
            {
                String name = set.getString("name");
                username = set.getString("username");
                String address = set.getString("address");
                String hobbies = set.getString("hobbies");
                return new User(name, username, address, hobbies);
            }
            return null;
        } catch (SQLException ex) {
            //TODO: Error handling
            ex.printStackTrace();
            return null;
        }
    }

    public static List<User> getAllUsers() {
        try {
            PreparedStatement stmt = getPreparedStatement("SELECT * FROM `sassy`.`User`", getUserConnection());

            ResultSet set = stmt.executeQuery();
            ArrayList<User> usersArray = new ArrayList<>();

            while (set.next()) {

                String name = set.getString("name");
                String username = set.getString("username");
                String address = set.getString("address");
                String hobbies = set.getString("hobbies");

                usersArray.add(new User(name, username, address, hobbies));

            }
            return usersArray;
        } catch (SQLException ex) {
            //TODO: Error handling
            ex.printStackTrace();
            return null;
        }

    }

    public static List<RelationshipType> getAllRelationshipTypes() {
        try {
            PreparedStatement stmt = getPreparedStatement("SELECT * FROM `sassy`.`RelationshipType`", getUserConnection());
            ResultSet set = stmt.executeQuery();
            ArrayList<RelationshipType> usersArray = new ArrayList<>();

            while (set.next()) {
                int id = set.getInt("id");
                String type = set.getString("type");
                usersArray.add(new RelationshipType(id, type));
            }
            return usersArray;
        } catch (SQLException ex) {
            //TODO: Error handling
            return null;
        }
    }

    public static boolean setRelationship(String fromUsername, String toUsername, int RelationshipTypeId) {
        return false;
    }
    
    private static PreparedStatement getPreparedStatement(String sql, Connection conn){
        if (!preparedStmts.containsKey(sql)) {
            try {
                preparedStmts.put(sql, conn.prepareStatement(sql));
            } catch (SQLException ex) {
                //TODO: Error handling 
            }
        }
        return preparedStmts.get(sql);
    }

    //<editor-fold desc="Connections">
    private static Connection getLoginConnection() {
        if (loginConnection == null) {
            try {
                Context ctx = new InitialContext();
                loginConnection = ((DataSource) ctx.lookup("java:comp/env/jdbc/login")).getConnection();
            } catch (Exception ex) {
                loginConnection = getConnection("login");
            }
        }
        return loginConnection;
    }

    private static Connection getUserConnection() {
        if (userConnection == null) {
            try {
                Context ctx = new InitialContext();
                userConnection = ((DataSource) ctx.lookup("java:comp/env/jdbc/user")).getConnection();
            } catch (Exception ex) {
                userConnection = getConnection("user");
            }
        }
        return userConnection;
    }

    private static Connection getAdminConnection() {
        if (adminConnection == null) {
            try {
                Context ctx = new InitialContext();
                adminConnection = ((DataSource) ctx.lookup("java:comp/env/jdbc/admin")).getConnection();
            } catch (Exception ex) {
                adminConnection = getConnection("admin");
            }
        }
        return adminConnection;
    }

    private static Connection getConnection(String role) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + database, roles.get(role)[0], roles.get(role)[1]);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //TODO: Error handling
        }
        return con;
    }
    //</editor-fold>

    private static void loadXML() {
        try {
            DOMParser parser = new DOMParser();
            parser.parse("src/java/Database/SQLConnection.conf.xml");
            Document doc = parser.getDocument();

            //Get host connection string
            {
                Node host = doc.getElementsByTagName("host").item(0);
                NamedNodeMap attr = host.getAttributes();
                url = attr.getNamedItem("url").getNodeValue();
                port = Integer.parseInt(attr.getNamedItem("port").getNodeValue());
                database = attr.getNamedItem("database").getNodeValue();
            }
            //Get user roles
            NodeList users = doc.getElementsByTagName("user");
            for (int i = 0; i < users.getLength(); i++) {
                Node userNode = users.item(i);
                NamedNodeMap attr = userNode.getAttributes();
                String role = attr.getNamedItem("name").getNodeValue();
                Node conn = userNode.getChildNodes().item(1);
                attr = conn.getAttributes();
                String user = attr.getNamedItem("user").getNodeValue();
                String password = attr.getNamedItem("password").getNodeValue();
                String[] strs = {user, password};
                roles.put(role, strs);
            }
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
