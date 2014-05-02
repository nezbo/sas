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
public class ViewUserBeanTest {
    
    private SecurityBean sb;
    private ViewUserBean vb;
    
    public ViewUserBeanTest() {
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
        vb = new ViewUserBean();
        vb.setSecurityBean(sb);
        
        DBConnection.deleteUser("testUser");
        DBConnection.deleteUser("testOtherUser");
        boolean worked = DBConnection.createUser("testUser", "password");
        boolean worked2 = DBConnection.createUser("testOtherUser", "password2");
        boolean worked3 = DBConnection.updateUserInfo("testUser", "testName", "testAddress", "testHobbies", "testFriends");
        boolean worked4 = DBConnection.updateUserInfo("testOtherUser", "testOtherName", "testOtherAddress", "testOtherHobbies", "testOtherFriends");
        
        assertTrue("ViewUserBeanTest setUp failed", worked && worked3 && worked2 && worked4);
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
    public void testGetUserSuccess(){
        vb.getUser("testUser");
        
        assertEquals("view other user's username, not logged in","",vb.getUsername());
        assertEquals("view other user's address, not logged in","",vb.getAddress());
        assertEquals("view other user's name, not logged in","testName",vb.getName());
        assertEquals("view other user's hobbies, not logged in","testHobbies",vb.getHobbies());
    }
    
    @Test
    public void testGetUserSelfSuccess(){
        sb.setLoginUserName("testUser");
        sb.setPassword("password");
        sb.login();
        vb.getUser("testUser");
        
        assertEquals("view own user's username, not logged in","testUser",vb.getUsername());
        assertEquals("view own user's address, not logged in","testAddress",vb.getAddress());
        assertEquals("view own user's name, not logged in","testName",vb.getName());
        assertEquals("view own user's hobbies, not logged in","testHobbies",vb.getHobbies());
    }
    
    @Test
    public void testSaveSuccess(){
        sb.setLoginUserName("testUser");
        sb.setPassword("password");
        sb.login();
        vb.getUser("testUser");
        
        vb.setAddress("newTestAddress");
        vb.save();
        
        String loadedAddress = DBConnection.getUser("testUser").getAddress();
        
        assertEquals("save own data success","newTestAddress",loadedAddress);
    }
    
    @Test
    public void testSaveOtherFail(){
        sb.setLoginUserName("testUser");
        sb.setPassword("password");
        sb.login();
        vb.getUser("testOtherUser");
        
        vb.setAddress("newTestAddress");
        vb.save();
        
        String loadedAddress = DBConnection.getUser("testOtherUser").getAddress();
        assertEquals("save other data fail","testOtherAddress",loadedAddress);
    }
}
