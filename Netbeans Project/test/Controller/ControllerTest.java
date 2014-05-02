/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.DBConnection;
import Model.RelationshipType;
import Model.User;
import java.util.List;
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
public class ControllerTest {
    
    private Controller c;
    
    public ControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        c = ControllerFactory.getController();
        
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

    // Tests directly on SecurityController
    @Test
    public void authUserSuccess(){
        boolean result = c.authenticate("testUser", "password");
        
        assertTrue("user authentication success",result);
    }
    
    @Test
    public void authUserWrongUsername(){
        boolean result = c.authenticate("testUserX", "password");
        
        assertFalse("user authentication wrong username",result);
    }
    
    @Test
    public void authUserWrongPassword(){
        boolean result = c.authenticate("testUser", "passwordX");
        
        assertFalse("user authentication wrong password",result);
    }
    
    @Test
    public void authUserLongUsername(){
        String username = String.valueOf(new char[50]);
        boolean result = c.authenticate(username, "password");
        
        assertFalse("user authentication long username",result);
    }
    
    @Test
    public void authUserLongPassword(){
        String password = String.valueOf(new char[300]);
        boolean result = c.authenticate("testUser", password);
        
        assertFalse("user authentication long password",result);
    }
    
    // ADMIN
    
    @Test
    public void authAdminSuccess(){
        boolean result = c.authenticateAdmin("testAdmin", "password");
        
        assertTrue("admin authentication success",result);
    }
    
    @Test
    public void authAdminWrongUsername(){
        boolean result = c.authenticateAdmin("testAdminX", "password");
        
        assertFalse("admin authentication wrong username",result);
    }
    
    @Test
    public void authAdminWrongPassword(){
        boolean result = c.authenticateAdmin("testAdmin", "passwordX");
        
        assertFalse("admin authentication wrong password",result);
    }
    
    @Test
    public void authAdminLongUsername(){
        String username = String.valueOf(new char[50]);
        boolean result = c.authenticateAdmin(username, "password");
        
        assertFalse("admin authentication long username",result);
    }
    
    @Test
    public void authAdminLongPassword(){
        String password = String.valueOf(new char[300]);
        boolean result = c.authenticateAdmin("testAdmin", password);
        
        assertFalse("Admin authentication long password",result);
    }
    
    @Test
    public void authGetUserSuccess(){
        User user = c.getUser("testUser");
        
        assertTrue("user fetched successfully",
                user.getName().equals("testName") && 
                        user.getAddress().equals("testAddress"));
    }
    
    @Test
    public void testGetUserFail(){
        User user = c.getUser("testUserX");
        
        assertTrue("fake user fetching",user == null);
    }
    
    @Test
    public void testGetUserNull(){
        User user = c.getUser(null);
        
        assertTrue("null user fetching",user == null);
    }
    
    @Test
    public void testGetAllUsersSuccess(){
        List<User> users = c.getAllUsers();
        
        assertTrue("getAllUsers success", users.size() > 1);
    }
    
    @Test
    public void testCreateUserLongUsername(){
        String username = String.valueOf(new char[50]);
        boolean result = c.createUser(username, "password");
        
        assertFalse("user creation long username",result);
    }
    
    @Test
    public void testCreateUserLongPassword(){
        String password = String.valueOf(new char[300]);
        boolean result = c.createUser("testUser2", password);
        
        assertFalse("user creation long password",result);
    }
    
    @Test
    public void testCreateExistingUser(){
        boolean result = c.createUser("testUser", "12345678");
        
        assertFalse("create existing user",result);
    }
    
    @Test
    public void testCreateNullUser(){
        boolean result = c.createUser(null, null);
        
        assertFalse("null user creation",result);
    }
    
    @Test
    public void testUpdatePasswordSuccess(){
        boolean result = c.updatePassword("testUser", "password2");
        
        assertTrue("update password success",result);
    }
    
    @Test
    public void testUpdateNullPassword(){
        boolean result = c.updatePassword("testUser", null);
        
        assertFalse("update password with null",result);
    }
    
    @Test
    public void testUpdatePasswordNullUser(){
        boolean result = c.updatePassword(null, "password");
        
        assertFalse("update password with null user",result);
    }
    
    @Test
    public void testUpdateInfoSuccess(){
        boolean result = c.updateUserInfo("testUser", "testName2", "testAddress2", "testHobbies2", "testFriends2");

        assertTrue("update user success",result);
        assertEquals("update user success data","testAddress2",DBConnection.getUser("testUser").getAddress());
    }
    
    @Test
    public void testUpdateInfoNullData(){
        boolean result = c.updateUserInfo("testUser", null, null, null, null);

        assertFalse("update user null",result);
        assertEquals("update user null no change","testAddress",DBConnection.getUser("testUser").getAddress());
    }
    
    @Test
    public void testUpdateInfoNull(){
        boolean result = c.updateUserInfo(null, "a", "b", "c", "d");

        assertFalse("update user null",result);
        assertEquals("update user null no change","testAddress",DBConnection.getUser("testUser").getAddress());
    }
    
    @Test
    public void testRelTypes(){
        List<RelationshipType> result = c.getRelationShipTypes();
        
        assertTrue("relationship types exist",result.size() > 0);
    }
    
    @Test
    public void testSetRelSuccess(){
        boolean result = c.setRelationship("testUser", "testOtherUser", 1);
        
        assertTrue("set relationship success",result);
        assertTrue("relationship exists",DBConnection.getRelationshipBetweenUsers("testUser", "testOtherUser") != null);
    }
    
    @Test
    public void testSetRelDuplicate(){
        c.setRelationship("testUser", "testOtherUser", 1);
        boolean result = c.setRelationship("testUser", "testOtherUser", 1);
        
        assertFalse("set relationship duplicate",result);
        assertTrue("relationship exists",DBConnection.getRelationshipBetweenUsers("testUser", "testOtherUser") != null);
    }
    
    @Test
    public void testSetRelDuplicateOpposite(){
        c.setRelationship("testUser", "testOtherUser", 1);
        boolean result = c.setRelationship("testOtherUser", "testUser", 1);
        
        assertFalse("set relationship duplicate backwards",result);
        assertTrue("relationship exists",DBConnection.getRelationshipBetweenUsers("testUser", "testOtherUser") != null);
    }
    
    @Test
    public void testSetRelInvalidID(){
        boolean result = c.setRelationship("testUser", "testOtherUser", -1);
        
        assertFalse("set relationship invalid id",result);
        assertTrue("relationship exists",DBConnection.getRelationshipBetweenUsers("testUser", "testOtherUser") == null);
    }
    
    @Test
    public void testSetRelNullUsers(){
        boolean result = c.setRelationship(null, "testOtherUser", 1);
        boolean result2 = c.setRelationship("testOtherUser","testUser", 1);
        
        assertFalse("set relationship null user",result && result2);
    }
    
    // TODO: MISSING TESTS FROM getAllUsersNotFriends and down (issue on Github)
}
