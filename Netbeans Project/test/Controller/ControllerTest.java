/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.DBConnection;
import Model.RelationshipType;
import Model.User;
import Model.Relationship;
import java.util.ArrayList;
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
    
    @Test 
    public void testGetRelSuccessZero(){
        List<Relationship> result = c.getRelationships("testUser");
        
        assertEquals("get relationships none success",0,result.size());
    }
    
    @Test 
    public void testGetRelSuccess(){
        DBConnection.setRelationship("testUser", "testOtherUser", 1);
        List<Relationship> result = c.getRelationships("testUser");
        
        assertEquals("get relationships success",1,result.size());
    }
    
    @Test 
    public void testGetRelSuccessOpposite(){ // relationships are one directional
        DBConnection.setRelationship("testOtherUser", "testUser", 1);
        List<Relationship> result = c.getRelationships("testUser");
        
        assertEquals("get relationships none success",0,result.size());
    }
    
    @Test 
    public void testGetRelNullUser(){ 
        List<Relationship> result = c.getRelationships(null);
        assertNull("get relationships null user",result);
    }
    
    @Test 
    public void testGetRelWrongUser(){ 
        List<Relationship> result = c.getRelationships("nonExistingUser");
        assertNull("get relationships wrong user",result);
    }
    
    @Test
    public void testGetNotFriendsSuccess(){
        List<User> result = c.getAllUsersNotFriends("testUser");
        // not friends with testOtherUser

        assertTrue("get not friends success",result.size() > 0);
    }
    
    @Test
    public void testGetNotFriendsIsNot(){
        DBConnection.setRelationship("testUser", "testOtherUser", 1);
        List<User> result = c.getAllUsersNotFriends("testUser");
        
        boolean found = false;
        for(User u : result){
            if(u.getUsername().equals("testOtherUser"))
                found = true;
        }

        assertFalse("get not friends is not friend",found);
    }
    
    @Test
    public void testGetNotFriendsNull(){
        List<User> result = c.getAllUsersNotFriends(null);
        
        assertNull("get not friends null user",result);
    }
    
    @Test
    public void testGetNotFriendsWrongUser(){
        List<Relationship> result = c.getRelationships("nonExistingUser");
        assertNull("get not friends wrong user",result);
    }
    
    // hugs
    @Test 
    public void testGetHugSuccessZero(){
        List<User> result = c.getHugs("testUser");
        
        assertEquals("get hugs none success",0,result.size());
    }
    
    @Test 
    public void testGetHugSuccess(){
        DBConnection.setRelationship("testUser", "testOtherUser", 1);
        DBConnection.addHug("testOtherUser", "testUser");
        List<Relationship> result = c.getRelationships("testUser");
        
        assertEquals("get hugs success",1,result.size());
    }
    
    @Test 
    public void testGetHugNullUser(){ 
        List<User> result = c.getHugs(null);
        System.out.println(result);
        assertNull("get hugs null user",result);
    }
    
    @Test 
    public void testGetHugWrongUser(){ 
        List<User> result = c.getHugs("nonExistingUser");
        System.out.println(result);
        assertNull("get hugs wrong user",result);
    }
    
    // give success
    @Test
    public void testGiveHugSuccess(){
        boolean result = c.giveHug("testUser", "testOtherUser");
        
        assertTrue("give hug success",result);
        assertTrue("give hug success exists",DBConnection.getHugUsers("testOtherUser").size() > 0);
    }
    
    // give twice (fail)
    @Test
    public void testGiveHugTwice(){
        c.giveHug("testUser", "testOtherUser");
        boolean result = c.giveHug("testUser", "testOtherUser");
        
        assertFalse("give hug twice",result);
        assertTrue("give hug twice exists",DBConnection.getHugUsers("testOtherUser").size() > 0);
    }
    
    // give null user
    @Test
    public void testGiveHugNullUser(){
        boolean result = c.giveHug(null, "testOtherUser");
        
        assertFalse("give hug null user",result);
        assertTrue("give hug null user not exist",DBConnection.getHugUsers("testOtherUser").isEmpty());
    }
    
    // give same user
    @Test
    public void testGiveHugSameUser(){
        boolean result = c.giveHug("testUser", "testUser");
        
        assertFalse("give hug same user",result);
        assertTrue("give hug same user not exist",DBConnection.getHugUsers("testUser").isEmpty());
    }
    
    // give wrong user
    @Test
    public void testGiveHugWrongUser(){
        boolean result = c.giveHug("testFakeUser", "testUser");
        
        assertFalse("give hug fake user",result);
        assertTrue("give hug fake user not exist",DBConnection.getHugUsers("testUser").isEmpty());
    }
    
    // remove success one
    @Test
    public void testRemoveHugSuccess(){
        DBConnection.addHug("testOtherUser", "testUser");
        User testOtherUser = DBConnection.getUser("testOtherUser");
        List<User> listOther = new ArrayList<User>(1);
        listOther.add(testOtherUser);
        
        boolean result = c.removeHugs("testUser", listOther);
        
        assertTrue("remove hug success",result);
        assertTrue("remove hug success removed", DBConnection.getHugUsers("testUser").isEmpty());
    }
    
    // remove success zero
    @Test
    public void testRemoveHugSuccessZero(){
        DBConnection.addHug("testOtherUser", "testUser");
        //User testOtherUser = DBConnection.getUser("testOtherUser");
        List<User> listOther = new ArrayList<User>(1);
        //listOther.add(testOtherUser);
        
        boolean result = c.removeHugs("testUser", listOther);
        
        assertTrue("remove hugs zero success",result);
        assertTrue("remove hugs zero none removed", DBConnection.getHugUsers("testUser").size() == 1);
    }
    
    // remove null list
    @Test
    public void testRemoveHugNullList(){
        DBConnection.addHug("testOtherUser", "testUser");
        
        boolean result = c.removeHugs("testUser", null);
        
        assertFalse("remove hugs null list",result);
        assertTrue("remove hugs null list - none removed", DBConnection.getHugUsers("testUser").size() == 1);
    }
    
    // remove null user
    @Test
    public void testRemoveHugNullUser(){
        DBConnection.addHug("testOtherUser", "testUser");
        User testOtherUser = DBConnection.getUser("testOtherUser");
        List<User> listOther = new ArrayList<User>(1);
        listOther.add(testOtherUser);
        
        boolean result = c.removeHugs(null, listOther);
        
        assertFalse("remove hug null user",result);
        assertFalse("remove hug null user - nones removed", DBConnection.getHugUsers("testUser").isEmpty());
    }
    
    // remove wrong user
    @Test
    public void testRemoveHugWrongUser(){
        DBConnection.addHug("testOtherUser", "testUser");
        User testOtherUser = DBConnection.getUser("testOtherUser");
        List<User> listOther = new ArrayList<User>(1);
        listOther.add(testOtherUser);
        
        boolean result = c.removeHugs("testFakeUser", listOther);
        
        assertFalse("remove hug wrong user",result);
        assertFalse("remove hug wrong user - nones removed", DBConnection.getHugUsers("testUser").isEmpty());
    }
    
    // admin delete success
    @Test
    public void testAdminDeleteSuccess(){
        boolean result = c.delete("testUser", "testAdmin", "password");
        
        assertTrue("admin delete success", result);
        assertNull("admin delete success - removed", DBConnection.getUser("testUser"));
    }
    
    // admin delete (not admin)
    @Test
    public void testAdminDeleteNotAdmin(){
        boolean result = c.delete("testUser", "testUser", "password");
        
        assertFalse("admin delete - not admin", result);
        assertNotNull("not admin delete - not removed", DBConnection.getUser("testUser"));
    }
    
    // admin delete wrong password
    @Test
    public void testAdminDeleteWrongPassword(){
        boolean result = c.delete("testUser", "testAdmin", "passwordxxx");
        
        assertFalse("admin delete - wrong password", result);
        assertNotNull("wrong password delete - not removed", DBConnection.getUser("testUser"));
    }
    
    // admin delete self (fail) - DO NOT RUN UNTIL IF WE CANT CREATE BY CODE
    /*@Test
    public void testAdminDeleteSelf(){
        boolean result = c.delete("testAdminUser", "testAdmin", "password");
        
        assertFalse("admin delete self", result);
        assertNotNull("admin delete self - not removed", DBConnection.getUser("testAdmin"));
    }*/
    
    // null admin delete
    @Test
    public void testAdminDeleteNullAdmin(){
        boolean result = c.delete("testUser", null, "password");
        
        assertFalse("admin delete - null admin", result);
        assertNotNull("null admin delete - not removed", DBConnection.getUser("testUser"));
    }
    
    // null password delete
    @Test
    public void testAdminDeleteNullPassword(){
        boolean result = c.delete("testUser", "testAdmin", null);
        
        assertFalse("admin delete - null password", result);
        assertNotNull("null password delete - not removed", DBConnection.getUser("testUser"));
    }
    
    // admin delete null user
    @Test
    public void testAdminDeleteNullUser(){
        int size = DBConnection.getAllUsers().size();
        boolean result = c.delete(null, "testAdmin", "password");
        
        assertFalse("admin delete - null user", result);
        assertTrue("null password delete - none removed", DBConnection.getAllUsers().size() == size);
    }
    
    // admin delete wrong user
    @Test
    public void testAdminDeleteWrongUser(){
        int size = DBConnection.getAllUsers().size();
        boolean result = c.delete("testWrongUser", "testAdmin", "password");
        
        assertFalse("admin delete - wrong user", result);
        assertTrue("wrong user delete - none removed", DBConnection.getAllUsers().size() == size);
    }
}
