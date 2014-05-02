/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Database.DBConnection;
import Model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emil
 */
public class AdminBeanTest {
    
    private AdminBean ab;
    private SecurityBean sb;
    
    public AdminBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        DBConnection.deleteUser("testUser");
    }
    
    @Before
    public void setUp() {
        ab = new AdminBean();
        sb = new SecurityBean();
        InformationBean ib = new InformationBean();
        
        ab.setSecurityBean(sb);
        ab.setInformationBean(ib);

        DBConnection.deleteUser("testUser");
        boolean worked = DBConnection.createUser("testUser", "password");
        assertTrue("AdminBeanTest setUp failed", worked);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testDeleteNotAuth(){
        User user = DBConnection.getUser("testUser");
        String result = ab.delete(user);
        
        assertEquals("Deleting user without admin auth", "usersFalse", result);
    }
    
    @Test
    public void testDeleteSuccess(){
        User user = DBConnection.getUser("testUser");
        sb.setLoginUserName("testAdmin");
        sb.setPassword("password");
        ab.setPassword("password");
        sb.adminLogin();
        
        String result = ab.delete(user);
        
        assertEquals("Deleting user correctly", "usersTrue", result);
    }
    
    @Test
    public void testDeleteNotExist(){
        User user = DBConnection.getUser("testUser");
        sb.setLoginUserName("testAdmin");
        sb.setPassword("password");
        ab.setPassword("password");
        sb.adminLogin();
        
        ab.delete(user);
        String result = ab.delete(user);
        
        assertEquals("Deleting non-existing user", "usersFalse", result);
    }
}
