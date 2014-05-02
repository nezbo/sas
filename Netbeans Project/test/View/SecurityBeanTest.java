/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Database.DBConnection;
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
public class SecurityBeanTest {
    
    private SecurityBean sb;
    
    public SecurityBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sb = new SecurityBean();
        
        DBConnection.deleteUser("testUser");
        boolean worked = DBConnection.createUser("testUser", "password");
        assertTrue("AdminBeanTest setUp failed", worked);
    }
    
    @After
    public void tearDown() {
    }

    // ------ USER METHODS ------
    
    @Test
    public void testLoginSuccess(){
        sb.setLoginUserName("testUser");
        sb.setPassword("password");
        
        String result = sb.login();
        assertEquals("User succesfully logging in - url", "user", result);
        assertTrue("User successfully logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testLoginWrongUsername(){
        sb.setLoginUserName("testFakeUser");
        sb.setPassword("password");
        
        String result = sb.login();
        assertEquals("Wrong username logging in - url", "index", result);
        assertFalse("Wrong username logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testLoginWrongPassword(){
        sb.setLoginUserName("testUser");
        sb.setPassword("passwordxxx");
        
        String result = sb.login();
        assertEquals("Wrong password logging in - url", "index", result);
        assertFalse("Wrong password logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testLogoutSuccess(){
        sb.setLoginUserName("testUser");
        sb.setPassword("password");
        sb.login();
        
        try{
            sb.logOut();
        }catch(NullPointerException e){/* Offline cant find session */}
        assertFalse("Successfully logging out", sb.isLoggedIn());
    }
    
    @Test
    public void testLogoutNotLoggedIn(){
        try{
            sb.logOut();
        }catch(NullPointerException e){/* Offline cant find session */}
        
        assertFalse("Logging out, not in", sb.isLoggedIn());
    }
    // ------ ADMIN METHODS ------
    
    @Test
    public void testAdminLoginSuccess(){
        sb.setLoginUserName("testAdmin");
        sb.setPassword("password");
        
        String result = sb.adminLogin();
        assertEquals("Admin succesfully logging in - url", "login", result);
        assertTrue("Admin successfully logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testAdminLoginWrongUsername(){
        sb.setLoginUserName("testFakeAdmin");
        sb.setPassword("password");
        
        String result = sb.adminLogin();
        assertEquals("Admin wrong username logging in - url", "adminLogin", result);
        assertFalse("Admin wrong username logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testAdminLoginWrongPassword(){
        sb.setLoginUserName("testAdmin");
        sb.setPassword("passwordxxx");
        
        String result = sb.adminLogin();
        assertEquals("Admin wrong password logging in - url", "adminLogin", result);
        assertFalse("Admin wrong password logging in", sb.isLoggedIn());
    }
    
    @Test
    public void testAdminLogout(){
        sb.setLoginUserName("testAdmin");
        sb.setPassword("password");
        sb.adminLogin();
        
        try{
            sb.logOut();
        }catch(NullPointerException e){/* Offline cant find session */}
        assertFalse("Admin successfully logging out", sb.isLoggedIn());
    }
}
