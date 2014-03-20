/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
    
    @Resource(name="jdbc/login")
    private static DataSource loginDatasource;
    @Resource(name="jdbc/user")
    private static DataSource userDatasource;
    @Resource(name="jdbc/admin")
    private static DataSource adminDatasource;
    
    
    private static Connection loginConnection;
    private static Connection  userConnection;
    private static Connection adminConnection;

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

    public static void main(String[] args) {
        System.out.println(url);
        System.out.println(port);
        System.out.println(database);
        System.out.println(validUserLogin("usder", "password"));
        System.out.println(validUserLogin("user", "pasdsword"));
        System.out.println(validUserLogin("user", "password"));
    }
    
    public static boolean validUserLogin(String username, String cleartextPassword){
        try {
            Connection conn = getLoginConnection();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("SELECT COUNT(*) AS count FROM User WHERE username = '"+username+"' AND password = MD5('"+cleartextPassword+"');");
            stmt.getResultSet().next();
            int count = (int) (long)stmt.getResultSet().getObject(1);
            return count > 0;
        } catch (SQLException ex) {
            //Unable to create prepared statement
            ex.printStackTrace();
            return false;
        }
    }

    private static Connection getLoginConnection() {
        try {
            Context ctx = new InitialContext();
            return ((DataSource)ctx.lookup("java:comp/env/jdbc/login")).getConnection();
            //return loginDatasource.getConnection();
        } catch (Exception ex) {
            //TODO: Error handling
            ex.printStackTrace();
        }
        if(loginConnection == null) loginConnection = getConnection("login");
        return loginConnection;
    }

    private static Connection getUserConnection() {
        if(userConnection == null) userConnection = getConnection("user");
        return userConnection;
    }

    private static Connection getAdminConnection() {
        if(adminConnection == null) adminConnection = getConnection("admin");
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
