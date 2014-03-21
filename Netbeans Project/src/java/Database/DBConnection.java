/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    private static PreparedStatement pLoginStmt;
    private static PreparedStatement pCreateUserStmt;
    private static PreparedStatement pUpdateUserStmt;
    private static PreparedStatement pGetUserStmt;

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
        System.out.println(updateUser("bingo", "password", "Sparta", "Spartacus", "Shouting 'THIS IS SPARTA!'", "whatever dude"));
        System.out.println(getUser("user"));
    }

    public static boolean validUserLogin(String username, String cleartextPassword) {
        try {
            PreparedStatement stmt = getUserLoginStatement();
            stmt.setString(1, username);
            stmt.setString(2, cleartextPassword);
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
            PreparedStatement stmt = getCreateUserStatement();
            stmt.setString(1, username);
            stmt.setString(2, cleartextPassword);
            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            //TODO: Error handling
            return false;
        }
    }

    public static boolean updateUser(String oldUsername, String cleartextPassword, String name, String address, String hobbies, String friends) {
        try {
            PreparedStatement stmt = getUpdateUserStatement();
            stmt.setString(1, cleartextPassword);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setString(4, hobbies);
            stmt.setString(5, oldUsername);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //TODO: Error handling
            return false;
        }
    }
    
    public static User getUser(String username) {
        try {
            PreparedStatement stmt = getGetUserStatement();
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();
            set.first();
            String name = set.getString("name");
            username = set.getString("username");
            String address = set.getString("address");
            String hobbies = set.getString("hobbies");
            return new User(name, username, address, hobbies);
        } catch (SQLException ex) {
            //TODO: Error handling
            ex.printStackTrace();
            return null;
        }
    }

    /*************************************
    *        Prepared Statements         *
    *************************************/
    //<editor-fold>
    private static PreparedStatement getUserLoginStatement() {
        if (pLoginStmt == null) {
            try {
                Connection conn = getLoginConnection();
                pLoginStmt = conn.prepareStatement("SELECT COUNT(*) AS `count` FROM `sassy`.`User`  WHERE `username` = ? AND `password` = MD5(?);");
            } catch (SQLException ex) {
                //TODO: Error handling 
            }
        }
        return pLoginStmt;
    }

    private static PreparedStatement getCreateUserStatement() {
        if (pCreateUserStmt == null) {
            try {
                Connection conn = getLoginConnection();
                pCreateUserStmt = conn.prepareStatement("INSERT INTO  `sassy`.`User` (`username`,`password`,`name`,`address`,`hobbies`) VALUES ( ?, MD5( ? ) ,  '',  '',  '');");
            } catch (SQLException ex) {
                //TODO: Error handling 
            }
        }
        return pCreateUserStmt;
    }

    private static PreparedStatement getUpdateUserStatement() {
        if (pUpdateUserStmt == null) {
            try {
                Connection conn = getUserConnection();
                pUpdateUserStmt = conn.prepareStatement("UPDATE `sassy`.`User` SET `password` = MD5(?),`name` = ?,`address` = ?,`hobbies` = ? WHERE `username` = ?;");
            } catch (SQLException ex) {
                //TODO: Error handling 
            }
        }
        return pUpdateUserStmt;
    }

    private static PreparedStatement getGetUserStatement() {
        if (pGetUserStmt == null) {
            try {
                Connection conn = getUserConnection();
                pGetUserStmt = conn.prepareStatement("SELECT * FROM `sassy`.`User` WHERE `username` = ?;");
            } catch (SQLException ex) {
                //TODO: Error handling 
            }
        }
        return pGetUserStmt;
    }
    //</editor-fold>
    
    /*************************************
    *             Connections            *
    *************************************/
    //<editor-fold>
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
