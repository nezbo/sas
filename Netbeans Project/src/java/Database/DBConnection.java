/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author marcher89
 */
public class DBConnection {
    private static Map<String, String[]> userList = new HashMap<>();
    private static String url;
    private static int port;
    private static String database;
    
    public static void main(String[] args) {
        loadXML();
        System.out.println(url);
        System.out.println(port);
        System.out.println(database);
    }
    
    private static void loadXML() {
        try {
            DOMParser parser = new DOMParser();
            parser.parse("src/database/SQLConnection.conf.xml");
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
                String password = attr. getNamedItem("password").getNodeValue();
                String[] strs = {user, password};
                userList.put(role, strs);
            }
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
